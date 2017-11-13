package org.let

import akka.actor.{ActorSystem, OneForOneStrategy, SupervisorStrategy}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.routing.{DefaultResizer, RoundRobinPool}
import akka.stream.ActorMaterializer
import org.let.http.LetShoutRoute
import org.let.twitter.{Tweets, TwitterClientFactory}

object Main extends App with Config {
  implicit val actorSystem = ActorSystem("letShout")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executor = actorSystem.dispatcher
  implicit val log = Logging(actorSystem, "letShout")

  Http().bindAndHandle(NewTweetHandlerRouter(), httpConfig.interface, httpConfig.port)
  log.info("server started at 8080")

  def NewTweetHandlerRouter(): Route = {
    val twitterClient = TwitterClientFactory.newTwitter(twitterConfig.debug,
      twitterConfig.oAuthConsumerKey, twitterConfig.oAuthConsumerSecret,
      twitterConfig.oAuthAccessToken, twitterConfig.oAuthAccessTokenSecret)

    val escalator = OneForOneStrategy() {
      case _ => SupervisorStrategy.Resume
    }
    val resizer = DefaultResizer(lowerBound = 5, upperBound = 15, messagesPerResize = 100)

    val tweetHandlerRouter = actorSystem.actorOf(
      RoundRobinPool(5, resizer = Some(resizer), supervisorStrategy = escalator)
        .props(Tweets.props(twitterClient)), "tweetHandlerRouter")

    LetShoutRoute(tweetHandlerRouter)
  }
}

