package au.edu.utscic.athanorserver.server

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import akka.util.ByteString
import au.edu.utscic.athanorserver.message.Json


/**
  * Created by andrew@andrewresearch.net on 17/7/17.
  */
trait AthanorApi extends GenericApi {

  val textRoutes: Route = path("text" / "rhetorical") {
      parameter('grammar) { grammar =>
        post {
          import akka.http.scaladsl.unmarshalling.PredefinedFromEntityUnmarshallers.byteStringUnmarshaller
          entity(as[ByteString]) { str =>
            if (str.isEmpty) complete("Text required for analysis")
            else {
              println(s"grammar: $grammar")
              val analysisMsg = Json.ByteStringAnalysis(str,grammar)
              complete(TextAnalysisHandler.analyse(analysisMsg))
            }
          }
        }
    }
  }

  override val customRoutes: Route = pathPrefix("analyse") {
    textRoutes
  }

}
