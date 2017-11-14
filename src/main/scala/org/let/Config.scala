package org.let

import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus
import net.ceedubs.ficus.readers.ArbitraryTypeReader

trait Config {

  import ArbitraryTypeReader._
  import Ficus._

  protected case class HttpConfig(interface: String, port: Int)

  protected case class TwitterConfig(oAuthConsumerKey: String, oAuthConsumerSecret: String, oAuthAccessToken: String, oAuthAccessTokenSecret: String)

  private val config = ConfigFactory.load()
  protected val httpConfig: HttpConfig = config.as[HttpConfig]("http")
  protected val twitterConfig: TwitterConfig = config.as[TwitterConfig]("twitter")
}

object Config extends Config
