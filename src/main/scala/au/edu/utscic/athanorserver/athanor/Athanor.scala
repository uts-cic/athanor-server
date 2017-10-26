package au.edu.utscic.athanorserver.athanor

import au.edu.utscic.athanorserver.data.RhetoricalImplicits
import au.edu.utscic.athanorserver.data.RhetoricalTypes._
import com.xerox.jatanor.JAtanor
import org.json4s.JsonAST.JValue
import org.json4s.NoTypeHints
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

// Imports used to read property file
import java.io.{File, FileInputStream}
import java.util.Properties
import com.typesafe.config.{Config, ConfigFactory}

// Imports used to join paths
import java.nio.file.Path
import java.nio.file.Paths

// Imports used for logging
import au.edu.utscic.athanorserver.StreamsContext.log

/**
  * Created by andrew@andrewresearch.net on 28/6/17.
  */
object Athanor {

  lazy val athanor = new JAtanor

//
// This property file can be used to set grammar path
//
  val propertyFileName = "athanor-server.properties"
//
// This is the program that we are trying to load
  val programName = "apply.kif"

//
// Get the local grammar path.
// This is set to a default value of /home_dir/athanor/grammar but can be manipulated
// by the grammar.localPath value in the property file or
// the ATHANOR_SERVER_LOCAL_GRAMMAR_PATH environmental variable.
// The precedence (High to Low) is:
// 1) Environment variable 2) Property value 3) Hardcoded value
//

  val homeDir = System.getProperty("user.home");
  val defaultLocalGrammarPath = homeDir + "/athanor/grammar/"
  val localGrammarPath = GrammarPath.getValue(defaultLocalGrammarPath,
               GrammarPath.getProperty(propertyFileName,"grammar.localPath" ),
               GrammarPath.getEnv("ATHANOR_SERVER_LOCAL_GRAMMAR_PATH"))

  log.info("local grammar path ={} ", localGrammarPath)
  val localGrammarFullPath = Paths.get(localGrammarPath).resolve(programName).toString()
  log.info("local grammar full path={}", localGrammarFullPath)

//
// Attempt the load of the local grammar file
//

//
// Note: The loader seems to return a 0 on success but I kept the
// handler value that is passed to ExecuteFunctionArray in case there are situations
// that I don't understand in which a positive handle id is returned and needs to
// be passed back.
//
  val localGrammarHandler = athanor.LoadProgram(localGrammarFullPath,"")
  val localGrammarLoaded = (localGrammarHandler >= 0)

  log.info("LocalGrammarLoaded={}", localGrammarLoaded)

//
// Get the docker grammar path, but only attempt the load if the local grammar path
// could not be loaded, as we maybe running in a docker environment.
// Note that we get and display the docker path even when the local grammar path
// was found and loaded, as this is useful to see the environment settings and trace
// problems.
// The docker path defaults to /opt/docker/grammar but its value can be manipulated by the
// grammar.dockerPath value in the property file, or the ATHANOR_SERVER_DOCKER_GRAMMAR_PATH
// environment variable. The precedence is the same as for local grammar files.

  val defaultDockerGrammarPath = "/opt/docker/grammar/"
  val dockerGrammarPath = GrammarPath.getValue(defaultDockerGrammarPath,
    GrammarPath.getProperty(propertyFileName,"grammar.dockerPath" ),
    GrammarPath.getEnv("ATHANOR_SERVER_DOCKER_GRAMMAR_PATH"))

  log.info("docker grammar path ={} ", dockerGrammarPath)
  val dockerGrammarFullPath = Paths.get(dockerGrammarPath).resolve(programName).toString()
  log.info("docker grammar full path={}", dockerGrammarFullPath)
  val dockerGrammarHandler = if (localGrammarHandler >= 0)
                               -1
                             else
                               // Attempt the docker grammar path load
                               athanor.LoadProgram(dockerGrammarFullPath, "")

  val dockerGrammarLoaded = (dockerGrammarHandler >= 0)

  log.info("dockerGrammarLoaded={}", dockerGrammarLoaded)

  val handler = if (localGrammarHandler >= 0) localGrammarHandler else dockerGrammarHandler

  def isGrammarParserLoaded: Boolean = localGrammarLoaded || dockerGrammarLoaded

  def parseJsonSentence(sent:String):ParsedSentence = {

    import RhetoricalImplicits._

    val json:JValue = parse(sent)
    val lexNodes:LexicalNodes = json(0)
    val constTree:ConstituentTree = json(1).extract[ConstituentTree]
    val deps:Dependencies = json(2).extract[Dependencies]
    (lexNodes,constTree,deps)
  }

  def parsedSentenceToJsonString(parsedSent:ParsedSentence):String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val l = write(parsedSent._1)
    val c = write(parsedSent._2).replaceAll("""(\"(?=[0-9]))|((?<=[0-9])\")""","") //remove quotes around Ints for json
    val d = write(parsedSent._3)
    s"[$l,$c,$d]"
  }

  def analyseParsedSentence(parsed:ParsedSentence):List[String] = {
    val jsonStr:String = parsedSentenceToJsonString(parsed)
    this.analyseJson(jsonStr)
  }

  def analyseJson(json:String):List[String] = {
    athanor.ExecuteFunctionArray(handler,"Apply",List(json).toArray).toList
  }
}

