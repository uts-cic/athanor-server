package au.edu.utscic.athanorserver.server

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ExceptionHandler, Route, StandardRoute}
import au.edu.utscic.athanorserver.message.Exception.UnknownAnalysisType
import de.heikoseeberger.akkahttpjson4s.Json4sSupport
import org.json4s._

/** The main API structure
  *
  * Provides the generic API version and config details.
  * Provides stub routes: ''customRoutes'', and ''adminRoutes''
  * which are replaced by [[au.edu.utscic.athanorserver.server.AthanorApi]]
  *
  * @todo - Need to wire in config information for API details.
  *
  */
//noinspection ForwardReference
trait GenericApi extends Json4sSupport {

  import au.edu.utscic.athanorserver.StreamsContext._

  val version = "v2"
  val details:String = "no details yet" // ApiInfo(Config.name,Config.description,Config.version,Config.colour)
  val healthEndPoint = "health"

  val athanorExceptionHandler = ExceptionHandler {
    case _: UnknownAnalysisType =>
      extractUri { uri =>
        log.error(s"Request to $uri did not include a valid analysis type")
        complete(HttpResponse(InternalServerError, entity = "The request did not include a valid analysis type"))
      }
  }

  implicit val formats: Formats = DefaultFormats
  implicit val jacksonSerialization: Serialization = jackson.Serialization

  def nothingHere: StandardRoute =  complete(ResponseMessage("There is nothing at this URL"))

  val routes: Route = handleExceptions(athanorExceptionHandler) {
    pathSingleSlash {
      get(complete(ResponseMessage("The current version of this API can be found at /"+version)))
    } ~
      pathPrefix(version) {
        customRoutes ~
          path(healthEndPoint) {
            get(complete(ResponseMessage("ok")))
          } ~
          apiDetails ~
          adminRoutes
      }
  }

  val customRoutes: Route = path("custom") { get(complete("")) }
  val adminRoutes: Route = path("admin") { get(complete(""))}
  val apiDetails: Route = pathEnd(complete(ResponseMessage(details)))
}





