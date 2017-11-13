package org.let.twitter


import akka.actor.{Actor, ActorLogging, Props}
import org.let.cache.TweetCache


object Tweets {
  def props(cache: TweetCache): Props = {
    Props(classOf[Tweets], cache).withDispatcher("blocking-dispatcher")
  }

  case class UserTweetsQuery(user: String, tweets: Int)

  case class UserTweets(user: String, tweets: Seq[Tweet])

  case class UserNotFound(twitterUser: String)

  case class Tweet(tweet: String, createdAt: String)

}


class Tweets(val cache: TweetCache) extends Actor with ActorLogging {

  import Tweets._

  implicit val ec = context.dispatcher


  def receive: Receive = {
    case userTweetsQuery: UserTweetsQuery => {
      sender() ! cache.getOrLoad(userTweetsQuery)
    }
  }


}