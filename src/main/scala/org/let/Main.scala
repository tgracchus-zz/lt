package org.let

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.let.http.LetShoutRoute
import org.let.tweets.Tweets

object Main extends App with Config {
  implicit val actorSystem = ActorSystem("letShout")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executor = actorSystem.dispatcher
  implicit val log = Logging(actorSystem, "letShout")
  implicit val tweetHandler: ActorRef = actorSystem.actorOf(Tweets.props(), "tweetHandler")

  Http().bindAndHandle(LetShoutRoute(tweetHandler), httpConfig.interface, httpConfig.port)
  log.info("server started at 8080")
}
