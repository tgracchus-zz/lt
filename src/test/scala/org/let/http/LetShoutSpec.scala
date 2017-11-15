package org.let.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.let.twitter.twitter4j.TwitterMock
import org.scalatest.{Matchers, WordSpec}

class LetShoutSpec extends WordSpec with Matchers with ScalatestRouteTest  with TwitterMock {

  val expectedJson = "{\"user\":\"user\",\"tweets\":[{\"tweet\":\"tweet1\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet2\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet3\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet4\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet5\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet6\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet7\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet8\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet9\",\"createdAt\":\"01/18/1970 12:37:57\"},{\"tweet\":\"tweet10\",\"createdAt\":\"01/18/1970 12:37:57\"}]}"

  "The LetShoutSpec" should {

    "return a greeting for GET requests to the root path" in {
      Get("/v1/letshout?user=user&tweets=10") ~> LetShoutRoute(system,twitter).route ~> check {
        responseAs[String] shouldEqual expectedJson
      }
    }
  }


}
