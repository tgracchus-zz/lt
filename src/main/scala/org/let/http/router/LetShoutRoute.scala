package org.let.http.router

import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Directives.{complete, get, pathPrefix}
import akka.http.scaladsl.server.Route

trait LetShoutRouter {
  protected implicit def log: LoggingAdapter

  private val apiVersion = "v1"

  protected def routes: Route = pathPrefix(apiVersion) {
    get {
      log.info("/status executed")
      complete("Test")
    }
  }
}

