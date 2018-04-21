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
      case message: SalesInputData =>{
          val json = Json.toJson(message)
          if(wsOut != null)
          wsOut ! Json.stringify(json)


      }
      case message: String => {
        println(" Pattern Matching "+message.toString())
        if(wsOut != null)
          {
                wsOut ! message
          }
      }
      case _ =>
    }

  }



}