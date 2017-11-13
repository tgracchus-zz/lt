package org.let.http

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import org.let.twitter.Tweets.{TwitterError, UserTweets, UserTweetsQuery}

import scala.concurrent.duration._

object LetShoutRoute {
  def apply(tweetHandler: ActorRef): Route = {
    new LetShoutRoute(tweetHandler).routes
  }
}

class LetShoutRoute(val tweetHandler: ActorRef) extends Directives with LetShoutJsonSupport {
  private val apiVersion = "v1"
  implicit val timeout = Timeout(10 seconds)

  private val routes: Route =
    pathPrefix(apiVersion) {
      path("letshout") {
        get {
          parameters(('user.as[String], 'tweets.as[Int] ? 50))
            .as(UserTweetsQuery) { userTweetsQuery =>
              onSuccess(tweetHandler ? userTweetsQuery) {
                case userTweets: UserTweets =>
                  complete(StatusCodes.OK, userTweets)
                case error: TwitterError =>
                  complete(StatusCodes.InternalServerError, error)
                case _ =>
                  complete(StatusCodes.InternalServerError)
              }
            }
        }
      }
    }
}

