package org.let

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.let.http.LetShoutRoute
import org.let.twitter.twitter4j.Twitter4JClient

object Main extends App with Config with LetShoutRoute {
  implicit val actorSystem = ActorSystem("letShout")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executor = actorSystem.dispatcher
  implicit val log = Logging(actorSystem, "letShout")
  implicit val twitter = Twitter4JClient.newTwitterClient(
    twitterConfig.oAuthConsumerKey, twitterConfig.oAuthConsumerSecret,
    twitterConfig.oAuthAccessToken, twitterConfig.oAuthAccessTokenSecret)

  Http().bindAndHandle(route, httpConfig.interface, httpConfig.port)
  log.info("server started at 8080")

}

