package org.let.twitter


import akka.actor.{Actor, ActorLogging, Props}
import org.let.cache.{ReadCache, TweetReadCache}
import org.let.twitter.Tweets.{UserTweets, UserTweetsQuery}


object Tweets {
  def props()(implicit tweetReadCache: ReadCache[UserTweetsQuery, UserTweets]): Props = {
    Props(classOf[Tweets], tweetReadCache).withDispatcher("blocking-dispatcher")
  }

  case class UserTweetsQuery(user: String, tweets: Int)

  case class UserTweets(user: String, tweets: Seq[Tweet])

  case class Tweet(tweet: String, createdAt: String)

  case class TwitterError(error: String)

}

class Tweets()(implicit val readCache: ReadCache[UserTweetsQuery, UserTweets]) extends Actor with TweetReadCache with ActorLogging {

  import Tweets._

  def receive: Receive = {
    case userTweetsQuery: UserTweetsQuery => {
      try {
        sender() ! getOrLoad(userTweetsQuery)
      } catch {

        case e: Exception => {
          log.error(e, e.getLocalizedMessage)
          sender() ! TwitterError(e.getLocalizedMessage)
        }
      }
    }
  }

}