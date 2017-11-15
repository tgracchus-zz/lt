package org.let

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.let.http.LetShoutRoute
import org.let.twitter.twitter4j.Twitter4JClient

object Main extends App with Config {
  implicit val system = ActorSystem("letShout")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executor = system.dispatcher
  implicit val log = Logging(system, "letShout")
  implicit val twitter = Twitter4JClient.newTwitterClient(
    twitterConfig.oAuthConsumerKey, twitterConfig.oAuthConsumerSecret,
    twitterConfig.oAuthAccessToken, twitterConfig.oAuthAccessTokenSecret)

  Http().bindAndHandle(LetShoutRoute(system, twitter).route, httpConfig.interface, httpConfig.port)
  log.info("server started at 8080")

}

