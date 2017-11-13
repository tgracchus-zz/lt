package org.let.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes.ServerError
import org.let.twitter.Tweets.{Tweet, TwitterError, UserTweets}
import spray.json.DefaultJsonProtocol


trait LetShoutJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val tweetFormat = jsonFormat2(Tweet)
  implicit val userTweetsFormat = jsonFormat2(UserTweets)
  implicit val twitterErrorFormat = jsonFormat1(TwitterError)
}