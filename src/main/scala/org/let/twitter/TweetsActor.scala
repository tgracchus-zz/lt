package org.let.twitter


import akka.actor.{Actor, ActorLogging, Props}
import org.let.cache.TweetCache
import org.let.twitter.twitter4j.TwitterClient


object TweetsActor {
  def props(): Props = {
    Props(classOf[TweetsActor]).withDispatcher("blocking-dispatcher")
  }

  case class UserTweetsQuery(user: String, tweets: Int)

  case class UserTweets(user: String, tweets: Seq[Tweet])

  case class Tweet(tweet: String, createdAt: String)

  case class TwitterError(error: String)

}

class TweetsActor(implicit val twitter: TwitterClient) extends Actor with TweetCache with ActorLogging {

  import TweetsActor._

  def receive: Receive = {
    case userTweetsQuery: UserTweetsQuery => {
      try {
        sender() ! tweetCache.getOrLoad(userTweetsQuery)
      } catch {

        case e: Exception => {
          log.error(e, e.getLocalizedMessage)
          sender() ! TwitterError(e.getLocalizedMessage)
        }
      }
    }
  }

}