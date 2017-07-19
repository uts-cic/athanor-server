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

import scala.collection.JavaConverters._

/**
  * Created by andrew@andrewresearch.net on 17/7/17.
  */
//noinspection SpellCheckingInspection,SpellCheckingInspection,SpellCheckingInspection,SpellCheckingInspection
object TextParser {

  def parse(text:String):List[ParsedSentence] = {
    val props = new Properties
    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse") // ner, dcoref
    val pipeline = new StanfordCoreNLP(props)
    val document = new Annotation(text)
    pipeline.annotate(document)

    val sentences = document.get(classOf[SentencesAnnotation]).asScala.toList

    sentences.map { sentence =>
      val tokens:List[CoreLabel] = sentence.get(classOf[TokensAnnotation]).asScala.toList
      val tree:Tree = sentence.get(classOf[TreeAnnotation])
      val dependencies:SemanticGraph = sentence.get(classOf[EnhancedPlusPlusDependenciesAnnotation])
      SentenceParser.parse(tokens,tree,dependencies)
    }

  }

  // This is the coreference link graph
  // Each chain stores a set of mentions that link to each other,
  // along with a method for getting the most representative mention
  // Both sentence and token offsets start at 1!
  //val graph = document.get(classOf[CorefChainAnnotation])

}
