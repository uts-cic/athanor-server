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

// Imports used to join paths

// Imports used for logging

/**
  * Created by andrew@andrewresearch.net on 28/6/17.
  */
object Athanor {

  val grammarPath = GrammarPath.currentPath

  val athanor = new JAtanor
  val aHandler = athanor.LoadProgram(grammarPath + "/apply_ana.kif","")
  val rHandler = athanor.LoadProgram(grammarPath + "/apply.kif","")


  def analyseParsedSentence(parsed:ParsedSentence,grammar:String):List[String] = {
    val jsonStr:String = parsedSentenceToJsonString(parsed)
    grammar match {
      case "analytic" => {


        println(s"analytic: $aHandler")
        athanor.ExecuteFunctionArray(aHandler,"Apply",List(jsonStr).toArray).toList
      }
      case "reflective" => {


        println(s"reflective: $rHandler")
        athanor.ExecuteFunctionArray(rHandler,"Apply",List(jsonStr).toArray).toList
      }
      case _ => List()
    }

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




}

