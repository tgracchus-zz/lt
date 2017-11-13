package org.let.twitter.twitter4j

import org.let.Config
import org.scalatest.{FlatSpec, Matchers}

class UserTwitter4JClientSpec extends FlatSpec with Matchers with Config {

  val twitterClient = Twitter4JClient.newTwitterClient(twitterConfig.debug,
    twitterConfig.oAuthConsumerKey, twitterConfig.oAuthConsumerSecret,
    twitterConfig.oAuthAccessToken, twitterConfig.oAuthAccessTokenSecret)

  "twitterClient" should "retrieve 10 tweets" in {
    val tweets = twitterClient.searchUserTweets("bcn_ajuntament", 10)
    assert(tweets.size() == 10)
  }

  "twitterClient" should "retrieve 101 tweets" in {
    val tweets = twitterClient.searchUserTweets("bcn_ajuntament", 101)
    assert(tweets.size() == 101)
  }

  "twitterClient" should "retrieve 101 tweets" in {
    val tweets = twitterClient.searchUserTweets("bcn_ajuntament", 101)
    assert(tweets.size() == 101)
  }

}
