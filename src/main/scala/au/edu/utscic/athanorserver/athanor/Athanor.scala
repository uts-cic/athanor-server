package au.edu.utscic.athanorserver.athanor

import au.edu.utscic.athanorserver.data.RhetoricalImplicits
import au.edu.utscic.athanorserver.data.RhetoricalTypes._
import com.typesafe.config.{Config, ConfigFactory}
import com.xerox.jatanor.JAtanor
import org.json4s.JsonAST.JValue
import org.json4s.NoTypeHints
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write

import scala.io.Source

/**
  * Created by andrew@andrewresearch.net on 28/6/17.
  */
object Athanor {

  lazy val config: Config = ConfigFactory.load()
  lazy val path: String = config.getString("app.path")
  lazy val athanor = new JAtanor
  lazy val handler = athanor.LoadProgram(program,"")
  lazy val program: String = fullPath("apply.kif")
  lazy val testSentence: String = fullPath("sentence.json")
  lazy val demoFile:String = Source.fromFile(testSentence).getLines.mkString

  def fullPath(file:String): String = {
    s"$path/scripts/$file"
  }


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

