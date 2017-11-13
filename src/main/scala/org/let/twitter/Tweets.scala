package org.let.twitter


import akka.actor.{Actor, ActorLogging, Props}

object Tweets {
  def props(): Props = {
    Props(classOf[Tweets])
  }

  case class UserTweetsQuery(twitterUser: String, tweets: Int)

  case class UserTweets(tweets: Seq[String])

  case class UserNotFound(twitterUser: String)

}


class Tweets extends Actor with ActorLogging {

  import Tweets._

  implicit val ec = context.dispatcher

  def receive: Receive = {
    case UserTweetsQuery(twitterUser, tweets) =>
      log.debug("Received UserTweetsQuery")
      sender() ! UserTweets(List(twitterUser + tweets))
  }
}