package au.edu.utscic.athanorserver.corenlp

import au.edu.utscic.athanorserver.data.RhetoricalTypes
import au.edu.utscic.athanorserver.data.RhetoricalTypes._
import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.semgraph.SemanticGraph
import edu.stanford.nlp.trees.Tree

import scala.collection.JavaConverters._
import scala.collection.immutable.SortedMap

/**
  * Created by andrew@andrewresearch.net on 12/7/17.
  */
object SentenceParser {

  def parse(tokens:List[CoreLabel],constituentTree:Option[Tree],dependencies:Option[SemanticGraph]):ParsedSentence = {
    val ln:LexicalNodes = getNodes(tokens)
    val ct:ConstituentTree = getTree(constituentTree)
    val dp:Dependencies = getDependencies(dependencies)
    (ln,ct,dp)
  }

  private def getOpt[T](a:Any):Option[T] = a match {
    case s:T => Some(s)
    case null => None
    case _ => None
  }

  def getNodes(tokens:List[CoreLabel]):LexicalNodes = {

      val nodes = tokens.zipWithIndex.map { case(token,i) =>
        val index = i+1
        val word = token.get(classOf[TextAnnotation])
        val lemma = token.get(classOf[LemmaAnnotation])
        val pos = token.get(classOf[PartOfSpeechAnnotation])
        val ne = token.get(classOf[NamedEntityTagAnnotation])
        val spk = token.get(classOf[SpeakerAnnotation])
        val left = token.beginPosition()
        val right = token.endPosition()
        (index,RhetoricalTypes.Node(index,pos,getOpt[String](word),getOpt[String](lemma),getOpt[String](ne),getOpt[String](spk),getOpt[Int](left),getOpt[Int](right)))
      }

    SortedMap[Int,Node](0 -> Node(0,"ROOT",None,None,None,None,None,None)) ++ nodes
  }

  def getTree(constituentTree: Option[Tree]):ConstituentTree = {
    ConstituentTreeParser.parse(constituentTree)
  }

  def getDependencies(dependencies:Option[SemanticGraph]):Dependencies = {
    dependencies match {
      case None => List()
      case Some(deps) => {
        deps.edgeListSorted().asScala.toList.map { d =>
          Dependency(
            d.getRelation.toString,
            d.getGovernor.backingLabel().index(),
            d.getDependent.backingLabel().index()
          )
        }
      }
    }
  }
}
