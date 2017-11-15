package org.let.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.util.Timeout
import org.let.twitter.TweetsActor.{TwitterError, UserTweets, UserTweetsQuery}
import org.let.twitter.TweetsActorRouter

import scala.concurrent.duration._

trait LetShoutRoute extends Directives with LetShoutJsonSupport with TweetsActorRouter {
  implicit val timeout = Timeout(10 seconds)
  private val apiVersion = "v1"

  val route: Route =
    pathPrefix(apiVersion) {
      path("letshout") {
        get {
          parameters(('user.as[String], 'tweets.as[Int] ? 50))
            .as(UserTweetsQuery) { userTweetsQuery =>
              onSuccess(tweetHandlerRouter ? userTweetsQuery) {
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

