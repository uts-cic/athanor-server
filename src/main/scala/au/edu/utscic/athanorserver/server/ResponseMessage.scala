package au.edu.utscic.athanorserver.server

/**
  * Created by andrew@andrewresearch.net on 17/7/17.
  */
case class ResponseMessage(message:String)

object ResponseMessage {
  def apply(message:Boolean) = new ResponseMessage(message.toString)
}