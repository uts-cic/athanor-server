package au.edu.utscic.athanorserver.data

import au.edu.utscic.athanorserver.data.RhetoricalTypes._
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JValue

import scala.collection.immutable.SortedMap

/**
  * Created by andrew@andrewresearch.net on 19/7/17.
  */
object RhetoricalImplicits {

  implicit val formats = DefaultFormats
  implicit def jvalueToLexicalNodes(jvalue:JValue):LexicalNodes = SortedMap[Int,Node]() ++ jvalue.extract[Map[Int,Node]]

}
