package org.let.twitter


import akka.actor.{Actor, ActorLogging, Props}
import org.let.cache.TweetCache


object TweetsActor {
  def props(twitter: TwitterClient): Props = {
    Props(classOf[TweetsActor], twitter).withDispatcher("blocking-dispatcher")
  }

  case class UserTweetsQuery(user: String, tweets: Int)

  case class UserTweets(user: String, tweets: Seq[Tweet])

  case class Tweet(tweet: String, createdAt: String)

  case class TwitterError(error: String)

}

class TweetsActor(val twitter: TwitterClient) extends Actor with TweetCache with ActorLogging {

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