package org.let

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.let.http.router.LetShoutRouter

object Main extends App with Config with LetShoutRouter {
  implicit val actorSystem = ActorSystem("letShout")
  implicit val actorMaterializer = ActorMaterializer()
  implicit val executor = actorSystem.dispatcher
  implicit val log = Logging(actorSystem, "letShout")

  Http().bindAndHandle(routes, httpConfig.interface, httpConfig.port)
  log.info("server started at 8080")
}
