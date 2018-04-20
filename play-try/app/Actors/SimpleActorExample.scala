package Actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.api.mvc.Action
import play.twirl.api.Html
import Utils.SalesInputData
import akka.stream.scaladsl.Source
import play.api.mvc._
import play.api.libs.streams._
import play.api.libs.json._

object SimpleActorExample {

  var wsOut: ActorRef = null

  class ProductActor extends Actor {
    implicit private val SalesWrites = Json.writes[SalesInputData]
    def receive = {
      case message: SalesInputData => message match {
        case SalesInputData(user_id, product_id, gender, age, occupation, city, city_tenure, marital_status, product_category1, product_category2, product_category3) =>
          //wsOut ! (user_id.toString())

          val json = Json.toJson(message)
          if(wsOut != null)
          wsOut ! Json.stringify(json)


      }
      case _ =>
    }

  }



}