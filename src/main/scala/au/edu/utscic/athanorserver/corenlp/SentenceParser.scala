package au.edu.utscic.athanorserver.corenlp

import au.edu.utscic.athanorserver.data.RhetoricalTypes
import au.edu.utscic.athanorserver.data.RhetoricalTypes._
import edu.stanford.nlp.ling.CoreAnnotations.{LemmaAnnotation, PartOfSpeechAnnotation, TextAnnotation}
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.semgraph.SemanticGraph
import edu.stanford.nlp.trees.Tree

import scala.collection.immutable.ListMap

import scala.collection.JavaConverters._

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
object SentenceParser {

  def parse(tokens:List[CoreLabel],constituentTree:Tree,dependencies:SemanticGraph):ParsedSentence = {
    val ln:LexicalNodes = getNodes(tokens)
    val ct:ConstituentTree = getTree(constituentTree)
    val dp:Dependencies = getDependencies(dependencies)
    (ln,ct,dp)
  }

  def getNodes(tokens:List[CoreLabel]):LexicalNodes = {

      val nodes = tokens.zipWithIndex.toSeq.map { case(token,i) =>
        val index = i+1
        val word = token.get(classOf[TextAnnotation])
        val lemma = token.get(classOf[LemmaAnnotation])
        val pos = token.get(classOf[PartOfSpeechAnnotation])
        //val ne = token.get(classOf[NamedEntityTagAnnotation])
        (index,RhetoricalTypes.Node(index,pos,Some(word),Some(lemma)))
      }

    ListMap() ++ nodes
  }

  def getTree(constituentTree: Tree):ConstituentTree = {
      ConstituentTreeParser.parse(constituentTree)
  }

  def getDependencies(dependencies:SemanticGraph):Dependencies = {
    List()
  }
}
