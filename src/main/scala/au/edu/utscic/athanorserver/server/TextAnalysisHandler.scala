package au.edu.utscic.athanorserver.server

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString
import au.edu.utscic.athanorserver.StreamsContext
import au.edu.utscic.athanorserver.athanor.Athanor
import au.edu.utscic.athanorserver.message.Exception.UnknownAnalysisType
import au.edu.utscic.athanorserver.message.Json

import scala.concurrent.Future

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */
object TextAnalysisHandler {

  import StreamsContext._

  def analyse(msg:Json.ByteStringAnalysis):Future[Json.Results] = {
    log.debug("Analysing text...")
    val pipeline = msg.analysisType match {
      case "rhetorical" => demo
  //  case "xip" => getAnalysis[DocumentXip]("xipAnalyser",msg,sender)
      case _ => {
        throw UnknownAnalysisType("Unknown analysis type")
      }
    }
    val pipelineResults = TextPipeline(msg.byteStr,pipeline).run
    Json.formatResults(pipelineResults,"Text Analysis Results")
  }

  val demo:Flow[ByteString,String,NotUsed] = Flow[ByteString].map { bs =>
    val donothing = bs
    Athanor.process(Athanor.demoFile).mkString(",")
  }

  case class TextPipeline(inputStr: ByteString, flow: Flow[ByteString,String,NotUsed]) {
    import StreamsContext.materializer
    val source = Source.single(inputStr)
    def run = source via flow runWith(Sink.head[String])
  }
}
