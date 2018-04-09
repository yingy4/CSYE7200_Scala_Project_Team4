package Actors

import akka.actor.{Actor, ActorSystem, Props}
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api.mvc.Action
import play.twirl.api.Html
import Utils.SalesInputData

object SimpleActorExample {

  class ProductActor extends Actor {

    def receive = {
      case message: Any => message match {
        case SalesInputData(product_id,_,_,_,_,_,_,_,_,_,_) => println(product_id)
      }
    }



  }

}