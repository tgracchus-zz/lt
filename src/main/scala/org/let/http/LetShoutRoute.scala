package org.let.http

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import org.let.twitter.Tweets.{UserNotFound, UserTweets, UserTweetsQuery}

import scala.concurrent.duration._

object LetShoutRoute {
  def apply(tweetHandler: ActorRef): Route = {
    new LetShoutRoute(tweetHandler).routes
  }
}

class LetShoutRoute(val tweetHandler: ActorRef) {
  private val apiVersion = "v1"
  implicit val timeout = Timeout(10 seconds)

  private val routes: Route =
    pathPrefix(apiVersion) {
      path("letshout" / Segment) { tweeterUser =>
        get {
          parameter('tweets.as[Int] ? 50) { tweets =>
            onSuccess(tweetHandler ? UserTweetsQuery(tweeterUser, tweets)) {
              case userTweets: UserTweets =>
                complete(StatusCodes.OK, userTweets.tweets.toString())
              case userNotFound: UserNotFound =>
                complete(StatusCodes.BadRequest, userNotFound.toString)
              case _ =>
                complete(StatusCodes.InternalServerError)
            }
          }
        }
      }
    }
}

