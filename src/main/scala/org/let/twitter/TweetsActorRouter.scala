package org.let.twitter

import akka.actor.{OneForOneStrategy, SupervisorStrategy}
import akka.routing.{DefaultResizer, RoundRobinPool}
import org.let.Main.actorSystem
import org.let.cache.TweetCache

trait TweetsActorRouter extends TweetCache {

  val escalator = OneForOneStrategy() {
    case _ => SupervisorStrategy.Resume
  }
  val resizer = DefaultResizer(lowerBound = 5, upperBound = 15, messagesPerResize = 100)

  implicit val tweetHandlerRouter = actorSystem.actorOf(
    RoundRobinPool(5, resizer = Some(resizer), supervisorStrategy = escalator)
      .props(TweetsActor.props()), "tweetHandlerRouter")

}
