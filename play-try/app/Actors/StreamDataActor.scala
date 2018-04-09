package Actors

import play.api.Logger
import akka.actor.{Actor, ActorSystem, Props, ActorRef}
import play.api.libs.json.Json


class StreamDataActor(out:ActorRef) extends Actor {

  def receive = {
    case "subscribe" =>
      Logger.info("Received a message")
      out ! ("Hiiiii")
  }
}

object StreamDataActor {
  def props(out: ActorRef) = Props(new StreamDataActor(out))
}