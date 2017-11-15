package org.let.http

import akka.actor.{ActorRef, ActorSystem, OneForOneStrategy, SupervisorStrategy}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.pattern.ask
import akka.routing.{DefaultResizer, RoundRobinPool}
import akka.util.Timeout
import org.let.twitter.TweetsActor.{TwitterError, UserTweets, UserTweetsQuery}
import org.let.twitter.{TweetsActor, TwitterClient}

import scala.concurrent.duration._


object LetShoutRoute {
  def apply(system: ActorSystem, twitter: TwitterClient): LetShoutRoute = {
    new LetShoutRoute(system, twitter)
  }
}

class LetShoutRoute(val system: ActorSystem, val twitter: TwitterClient) extends Directives with LetShoutJsonSupport {
  implicit val timeout = Timeout(10 seconds)
  private val apiVersion = "v1"


  val escalator: OneForOneStrategy = OneForOneStrategy() {
    case _ => SupervisorStrategy.Resume
  }
  val resizer = DefaultResizer(lowerBound = 5, upperBound = 15, messagesPerResize = 100)

  val tweetHandlerRouter: ActorRef = system.actorOf(
    RoundRobinPool(5, resizer = Some(resizer), supervisorStrategy = escalator)
      .props(TweetsActor.props(twitter)), "tweetHandlerRouter")

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

