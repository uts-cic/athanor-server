package au.edu.utscic.athanorserver.corenlp

import java.util.Properties

import au.edu.utscic.athanorserver.data.RhetoricalTypes.ParsedSentence
import edu.stanford.nlp.ling.CoreAnnotations._
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.semgraph.SemanticGraph
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation
import edu.stanford.nlp.trees.Tree
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation
import edu.stanford.nlp.util.CoreMap

import scala.collection.JavaConverters._

/**
  * Created by andrew@andrewresearch.net on 17/7/17.
  */
//noinspection SpellCheckingInspection,SpellCheckingInspection,SpellCheckingInspection,SpellCheckingInspection
object TextParser {

  val pipeline:StanfordCoreNLP = {
    val props = new Properties
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse") //ner dcoref
    new StanfordCoreNLP(props)
  }

  def parse(text:String):List[ParsedSentence] = {
    val sentences = annotateSentences(text)
    parseSentences(sentences)
  }

  def annotateSentences(text:String):List[CoreMap] = {
    val document = new Annotation(text)
    pipeline.annotate(document)
    document.get(classOf[SentencesAnnotation]).asScala.toList
  }

  def parseSentences(sentences:List[CoreMap]):List[ParsedSentence] = {
    sentences.map { sentence =>
      val tokens = getTokens(sentence)
      val tree:Tree = sentence.get(classOf[TreeAnnotation])
      val dependencies:SemanticGraph = sentence.get(classOf[EnhancedPlusPlusDependenciesAnnotation])
      SentenceParser.parse(tokens,tree,dependencies)
    }
  }

  def getTokens(sentence:CoreMap):List[CoreLabel] = sentence.get(classOf[TokensAnnotation]).asScala.toList

  // This is the coreference link graph
  // Each chain stores a set of mentions that link to each other,
  // along with a method for getting the most representative mention
  // Both sentence and token offsets start at 1!
  //val graph = document.get(classOf[CorefChainAnnotation])

}
