package au.edu.utscic.athanorserver.server

import akka.http.scaladsl.Http

import scala.concurrent.Future


/** The akka-http server
  *
  * The server is started by [[au.edu.utscic.athanorserver.Application]]
  * The generic API structure is described by [[au.edu.utscic.athanorserver.server.GenericApi]]
  * which is bound to the server through the ''routes'' value.
  * Specific endpoints are specified by [[au.edu.utscic.athanorserver.server.AthanorApi]] which
  * inherits the ''GenericApi''.
  *
  * The ActorSystem, Materializer and Context are imported with [[au.edu.utscic.athanorserver.StreamsContext]]
  *
  */
object Server extends AthanorApi {

  import au.edu.utscic.athanorserver.StreamsContext._

  def startServer(address:String, port:Int): Future[Http.ServerBinding] = {
    log.info("Starting http server at {}:{}",address,port)
    Http().bindAndHandle(routes,address,port)
  }
}

