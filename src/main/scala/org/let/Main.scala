package org.let

import akka.actor.{ActorSystem, OneForOneStrategy, SupervisorStrategy}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.routing.{DefaultResizer, RoundRobinPool}
import akka.stream.ActorMaterializer
import org.let.cache.{ReadCache, TweetCacheLoader}
import org.let.cache.caffeine.CaffeineReadCache
import org.let.http.LetShoutRoute
import org.let.twitter.Tweets
import org.let.twitter.Tweets.{UserTweets, UserTweetsQuery}
import org.let.twitter.twitter4j.Twitter4JClient

object Main extends App with Config {
  implicit val actorSystem = ActorSystem("letShout")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executor = actorSystem.dispatcher
  implicit val log = Logging(actorSystem, "letShout")
  implicit val twitter = Twitter4JClient.newTwitterClient(twitterConfig.debug,
    twitterConfig.oAuthConsumerKey, twitterConfig.oAuthConsumerSecret,
    twitterConfig.oAuthAccessToken, twitterConfig.oAuthAccessTokenSecret)

  Http().bindAndHandle(NewTweetHandlerRouter(), httpConfig.interface, httpConfig.port)
  log.info("server started at 8080")

  def NewTweetHandlerRouter(): Route = {
    val cacheLoader: TweetCacheLoader = new TweetCacheLoader()
    implicit val readCache: ReadCache[UserTweetsQuery, UserTweets] = new CaffeineReadCache[UserTweetsQuery, UserTweets](cacheLoader)

    val escalator = OneForOneStrategy() {
      case _ => SupervisorStrategy.Resume
    }
    val resizer = DefaultResizer(lowerBound = 5, upperBound = 15, messagesPerResize = 100)

    val tweetHandlerRouter = actorSystem.actorOf(
      RoundRobinPool(5, resizer = Some(resizer), supervisorStrategy = escalator)
        .props(Tweets.props()), "tweetHandlerRouter")


    LetShoutRoute(tweetHandlerRouter)
  }
}

