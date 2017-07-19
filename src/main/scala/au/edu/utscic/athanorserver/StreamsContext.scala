package au.edu.utscic.athanorserver

import akka.actor.ActorSystem
import akka.event.Logging
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by andrew@andrewresearch.net on 20/2/17.
  */

object StreamsContext {
  implicit val system = ActorSystem()
  implicit val executor: ExecutionContextExecutor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val log = Logging(system.eventStream,"~")
}

