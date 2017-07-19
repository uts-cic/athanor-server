package au.edu.utscic.athanorserver.athanor

import au.edu.utscic.athanorserver.data.RhetoricalTypes.{ConstituentTree, Dependencies, LexicalNodes, ParsedSentence}
import com.typesafe.config.{Config, ConfigFactory}
import com.xerox.jatanor.JAtanor
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.parse
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write
import org.json4s.{DefaultFormats, NoTypeHints}

import scala.io.Source


/**
  * Created by andrew@andrewresearch.net on 28/6/17.
  */
object Athanor {

  lazy val config: Config = ConfigFactory.load()
  lazy val path: String = config.getString("app.path")
  lazy val athanor = new JAtanor
  lazy val handler = athanor.LoadProgram(program,"")

  def fullPath(file:String): String = {
    s"$path/scripts/$file"
  }

  def program: String = fullPath("apply.kif")


  def testSentence: String = fullPath("sentence.json")

  def demoFile:String = {
    Source.fromFile(testSentence).getLines.mkString
  }

  def demoParsed:ParsedSentence = {
    implicit val formats = DefaultFormats
    val sent = demoFile
    val json:JValue = parse(sent)
    val lexNodes:LexicalNodes = json(0).extract[LexicalNodes]
    val constTree:ConstituentTree = json(1).extract[ConstituentTree]
    val deps:Dependencies = json(2).extract[Dependencies]
    (lexNodes,constTree,deps)
  }

  def process(json:String):List[String] = {
    athanor.ExecuteFunctionArray(handler,"Apply",List(json).toArray).toList
  }

  def process(parsed:ParsedSentence):List[String] = {
    implicit val formats = Serialization.formats(NoTypeHints)
    val l = write(parsed._1)
    val c = write(parsed._2)
    val d = write(parsed._3)
    val output:String = s"[$l,$c,$d]"
    this.process(output)
  }

}

