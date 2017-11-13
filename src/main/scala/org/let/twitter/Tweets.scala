package org.let.twitter


import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.{Actor, ActorLogging, Props}
import twitter4j.Twitter

import scala.collection.JavaConverters._

object Tweets {
  def props(twitter: Twitter): Props = {
    Props(classOf[Tweets], twitter).withDispatcher("blocking-dispatcher")
  }

  case class UserTweetsQuery(user: String, tweets: Int)

  case class UserTweets(user: String, tweets: Seq[Tweet])

  case class UserNotFound(twitterUser: String)

  case class Tweet(tweet: String, createdAt: String)

}


class Tweets(val twitter: Twitter) extends Actor with ActorLogging {

  import Tweets._

  implicit val ec = context.dispatcher

  private val df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")

  def receive: Receive = {
    case UserTweetsQuery(twitterUser, count) => {
      val tweets = TweetSearch.userTweetSearch(twitter, twitterUser, count)
        .asScala.toList.map(tweet => {
        new Tweet(tweet = tweet.getText, createdAt = df.format(tweet.getCreatedAt))
      })
      sender() ! UserTweets(twitterUser, tweets)
    }

  }


}