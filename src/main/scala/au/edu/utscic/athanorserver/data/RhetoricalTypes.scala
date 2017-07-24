package au.edu.utscic.athanorserver.data

import scala.collection.immutable.SortedMap

/**
  * Created by andrew@andrewresearch.net on 29/6/17.
  */

object RhetoricalTypes {

  type LexicalNodes = SortedMap[Int,Node]
  type ConstituentTree = List[Any]
  type Dependencies = List[Dependency]

  type ParsedSentence = (LexicalNodes,ConstituentTree,Dependencies)

  case class Node(
                   id:Int,
                   POS:String,
                   surface:Option[String],
                   lemma:Option[String],
                   NER:Option[String] = None,
                   Speaker:Option[String] = None,
                   left:Option[Int] = None,
                   right:Option[Int] = None
                 )

  case class Dependency(
                         name:String,
                         governor:Int,
                         dependent:Int
                       )
}
