package org.let.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.let.twitter.Tweets.{Tweet, UserNotFound, UserTweets}
import spray.json.DefaultJsonProtocol


trait LetShoutJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val tweetFormat = jsonFormat2(Tweet)
  implicit val userTweetsFormat = jsonFormat2(UserTweets)
  implicit val userNotFoundFormat = jsonFormat1(UserNotFound)
}