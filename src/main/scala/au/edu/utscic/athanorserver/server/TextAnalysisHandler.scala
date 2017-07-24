package au.edu.utscic.athanorserver.server

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString
import au.edu.utscic.athanorserver.StreamsContext
import au.edu.utscic.athanorserver.athanor.Athanor
import au.edu.utscic.athanorserver.corenlp.TextParser
import au.edu.utscic.athanorserver.message.Exception.UnknownAnalysisType
import au.edu.utscic.athanorserver.message.Json
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

import scala.concurrent.Future

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
object TextAnalysisHandler {

  import StreamsContext._

  def analyse(msg:Json.ByteStringAnalysis):Future[Json.Results] = {
    log.info("Analysing text...")
    val pipeline = msg.analysisType match {
      case "rhetorical" => process
      case "demo" => demo
  //  case "xip" => getAnalysis[DocumentXip]("xipAnalyser",msg,sender)
      case _ =>
        throw UnknownAnalysisType("Unknown analysis type")
    }
    val pipelineResults = TextPipeline(msg.byteStr,pipeline).run
    Json.formatResults(pipelineResults,"Text Analysis Results")
  }

  val process:Flow[ByteString,String,NotUsed] = Flow[ByteString].map { bs =>
    val inputText = bs.utf8String
    val start = System.currentTimeMillis()
    val parsedSentences = TextParser.parse(inputText)
    val mid = System.currentTimeMillis()
    val nlpTime = (mid-start)
    val results = parsedSentences.map { ps =>
      val jsonString:String = Athanor.parsedSentenceToJsonString(ps)
      Athanor.analyseJson(jsonString)
    }
    val end = System.currentTimeMillis()
    val athTime = (end-mid)
    log.info("Process times for {} characters - coreNlp: {} athanor: {}",inputText.length,nlpTime,athTime)
    implicit val formats = Serialization.formats(NoTypeHints)
    import Serialization.write
    write(results)
  }

  val demo:Flow[ByteString,String,NotUsed] = Flow[ByteString].map { bs =>
    //noinspection ScalaUnusedSymbol
    //noinspection SpellCheckingInspection
    //noinspection ScalaUnusedSymbol
    val donothing = bs
    Athanor.analyseJson(Athanor.demoFile).mkString(",")
  }

  case class TextPipeline(inputStr: ByteString, flow: Flow[ByteString,String,NotUsed]) {
    import StreamsContext.materializer
    val source: Source[ByteString, NotUsed] = Source.single(inputStr)
    def run: Future[String] = source via flow runWith Sink.head[String]
  }
}
