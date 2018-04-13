package Actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api.mvc.Action
import play.twirl.api.Html
import Utils.SalesInputData
import akka.stream.scaladsl.Source
import play.api.mvc._
import play.api.libs.streams._

object SimpleActorExample {

  var wsOut: ActorRef = null

  class ProductActor extends Actor {

    def receive = {
      case message: Any => message match {
        case SalesInputData(product_id, _, _, _, _, _, _, _, _, _, _) =>
          wsOut ! (product_id.toString())

      }
    }

  }

}