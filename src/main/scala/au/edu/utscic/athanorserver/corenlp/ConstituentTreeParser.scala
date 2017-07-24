package au.edu.utscic.athanorserver.corenlp

import au.edu.utscic.athanorserver.data.RhetoricalTypes.ConstituentTree
import edu.stanford.nlp.trees.Tree
import org.json4s
import org.json4s.{JValue, NoTypeHints}

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
object ConstituentTreeParser {

  def parse(tree:Option[Tree]):ConstituentTree = {
    tree match {
      case None => List()
      case Some(t) => {
        val treeList = process(t)
        treeList.asInstanceOf[ConstituentTree]
      }
    }
  }

  def process(tree:Tree):Any = {
    import scala.collection.JavaConverters._
    if(tree.numChildren()==0) {
      tree.yieldWords().asScala.map(_.value()).mkString(",")
    }
    else {
      tree.label().toString +:
        tree.getChildrenAsList.asScala.map(t => process(t)).toList
    }
  }

  def convertToJsonString(ct:ConstituentTree):String =  {
    import org.json4s.jackson.Serialization
    implicit val formats = Serialization.formats(NoTypeHints)
    Serialization.write(ct)
  }

  def jsonStringToJValue(s:String): JValue = {
    import json4s._
    import json4s.jackson.JsonMethods
    JsonMethods.parse(s)
  }

//  private def parse(s: String, l: List[Any]): (String, List[Any]) = {
//    if (s.nonEmpty) {
//      s.head match {
//        case '(' => {
//          val (s2, l2) = parse(s.tail.trim, List())
//          parse(s2, l :+ l2)
//        }
//        case ')' => {
//          (s.tail.trim, l)
//        }
//        case _ => {
//          val (str, elm) = getElement(s)
//          parse(str.trim, l :+ elm)
//        }
//      }
//    } else ("", l)
//  }
//
//  private def getElement(s: String, el: String = ""): (String, String) = {
//    if (s.startsWith(")")) (s, el)
//    else if (s.startsWith(" ")) (s.tail, el)
//    else getElement(s.tail, (el + s.head))
//  }

}
