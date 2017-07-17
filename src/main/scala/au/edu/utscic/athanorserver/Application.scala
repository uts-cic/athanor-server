package au.edu.utscic.athanorserver

import au.edu.utscic.athanorserver.server.Server

/** The Application entry point starts the akka-http server
  *
  * @todo - Need to wire in config
  *
  */
object Application extends App {
  Server.startServer("0.0.0.0",8083)
}
