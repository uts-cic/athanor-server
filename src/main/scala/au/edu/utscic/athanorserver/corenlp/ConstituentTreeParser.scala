package au.edu.utscic.athanorserver.corenlp

import java.io.Serializable

import edu.stanford.nlp.trees.Tree
import org.json4s
import org.json4s.NoTypeHints

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
object ConstituentTreeParser {

  def parse(tree:Tree):String = {
    val treeList = process(tree)
    listToJsonString(treeList.asInstanceOf[List[Serializable]])
  }

  def process(tree:Tree):Any = {
    import scala.collection.JavaConverters._
    if(tree.numChildren()==0) {
      tree.yieldWords().asScala.mkString(",")
      //tree.taggedLabeledYield().asScala.toList.map(_.word())
    }
    else {
      tree.label().toString +:
        tree.getChildrenAsList.asScala.map(t => process(t)).toList
    }
  }

//  def makeJson(treeList:Any) =  {
//    import org.json4s
//    import org.json4s.NoTypeHints
//    import org.json4s._
//    import org.json4s.jackson.Serialization
//    import org.json4s.jackson.Serialization._
//    implicit val formats = Serialization.formats(NoTypeHints)
//    Serialization.write(treeList.asInstanceOf[List[Any]])
//  }
//  def parseNlpTree(s: String):String = {
//    val l = parseToList(s)
//    val nl = if(l.head=="TOP") "ROOT" +: l.tail else l //Change TOP to ROOT for athanor
//    listToJsonString(nl)
//  }

  //def parseToList(s:String) = this.parse(s, List())._2.head.asInstanceOf[List[Serializable]]

  def listToJsonString(l:List[Serializable]):String =  {
    import org.json4s.jackson.Serialization
    implicit val formats = Serialization.formats(NoTypeHints)
    Serialization.write(l)
  }

  def jsonStringToJValue(s:String) = {
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
