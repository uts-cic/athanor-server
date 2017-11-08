package au.edu.utscic.athanorserver.server

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString
import au.edu.utscic.athanorserver.StreamsContext
import au.edu.utscic.athanorserver.athanor.Athanor
import au.edu.utscic.athanorserver.corenlp.TextParser
import au.edu.utscic.athanorserver.message.Exception.UnknownAnalysisType
import au.edu.utscic.athanorserver.message.Json

import scala.concurrent.Future
/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
object TextAnalysisHandler {

  import StreamsContext._

  def analyse(msg:Json.ByteStringAnalysis):Future[Json.Results] = {
    log.info("Analysing text...")
    val pipeline = msg.analysisType match {
      case "rhetorical" => process //TODO Need to handle rhetorical?grammar=[analytic,reflective,...]
      //case "demo" => demo
  //  case "xip" => getAnalysis[DocumentXip]("xipAnalyser",msg,sender)
      case _ =>
        throw UnknownAnalysisType("Unknown analysis type")
    }
    val pipelineResults = Source.single(msg.byteStr) via this.process runWith(Sink.head[List[List[String]]]) //TextPipeline(msg.byteStr,pipeline).run
    Json.formatResults(pipelineResults,"Text Analysis Results")
  }

  val process:Flow[ByteString,List[List[String]],NotUsed] = Flow[ByteString].map { bs =>
    val inputText = bs.utf8String
    val start = System.currentTimeMillis()
    val parsedSentences = TextParser.parse(inputText)
    val mid = System.currentTimeMillis()
    val nlpTime = (mid-start)
    val results = parsedSentences.map { ps =>
      Athanor.analyseParsedSentence(ps)
    }
    val end = System.currentTimeMillis()
    val athTime = (end-mid)
    log.info("Process times for {} characters - coreNlp: {} athanor: {}",inputText.length,nlpTime,athTime)
    results
  }

//  val demo:Flow[ByteString,String,NotUsed] = Flow[ByteString].map { bs =>
//    //noinspection ScalaUnusedSymbol
//    //noinspection SpellCheckingInspection
//    //noinspection ScalaUnusedSymbol
//    val donothing = bs
//    Athanor.analyseJson(Athanor.demoFile).mkString(",")
//  }

  case class TextPipeline(inputStr: ByteString, flow: Flow[ByteString,List[List[String]],NotUsed]) {
    import StreamsContext.materializer
    val source: Source[ByteString, NotUsed] = Source.single(inputStr)
    def run = source via flow runWith Sink.head[List[List[String]]]
  }
}
