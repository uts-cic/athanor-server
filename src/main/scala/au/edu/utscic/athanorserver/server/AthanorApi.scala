package au.edu.utscic.athanorserver.server

import akka.http.scaladsl.server.Directives.{as, complete, entity, extractUnmatchedPath, pathPrefix, post}
import akka.http.scaladsl.server.Route
import akka.util.ByteString
import au.edu.utscic.athanorserver.message.Json

/**
  * Created by andrew@andrewresearch.net on 17/7/17.
  */
trait AthanorApi extends GenericApi {

  val textRoutes: Route = pathPrefix("text") {
    extractUnmatchedPath { param =>
      if (!param.isEmpty) {
        post {
          import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.byteStringUnmarshaller
          entity(as[ByteString]) { str =>
            if (str.isEmpty) complete("Text required for analysis")
            else {
              val analysisType = param.dropChars(1).head.toString
              //println("analysisType - {}",analysisType)
              val analysisMsg = Json.ByteStringAnalysis(str,analysisType)
              complete(TextAnalysisHandler.analyse(analysisMsg))
            }
          }
        }
      }
      else nothingHere
    }
  }

  override val customRoutes: Route = pathPrefix("analyse") {
    textRoutes
  }

}
