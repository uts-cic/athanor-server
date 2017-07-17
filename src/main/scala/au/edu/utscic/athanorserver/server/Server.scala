package au.edu.utscic.athanorserver.server


/** The akka-http server
  *
  * The server is started by [[au.edu.utscic.tap.Application]]
  * The generic API structure is described by [[au.edu.utscic.tap.httpServer.GenericApi]]
  * which is bound to the server through the ''routes'' value.
  * Specific endpoints are specified by [[au.edu.utscic.tap.httpServer.TapStreamApi]] which
  * inherits the ''GenericApi''.
  *
  * The ActorSystem, Materializer and Context are imported with [[au.edu.utscic.tap.TapStreamContext]]
  *
  */
object Server extends AthanorApi {

  def startServer(address:String, port:Int) = {
    log.info("Starting http server at {}:{}",address,port)
    Http().bindAndHandle(routes,address,port)
  }
}

