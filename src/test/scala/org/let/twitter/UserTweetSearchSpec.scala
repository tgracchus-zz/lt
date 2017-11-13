package org.let.twitter

import org.let.Config
import org.scalatest.{FlatSpec, Matchers}

class UserTweetSearchSpec extends FlatSpec with Matchers with Config {

  "TweetFetcher" should "retrieve my tweets" in {

    val twitterClient = TwitterClientFactory.newTwitter(twitterConfig.debug,
      twitterConfig.oAuthConsumerKey, twitterConfig.oAuthConsumerSecret,
      twitterConfig.oAuthAccessToken, twitterConfig.oAuthAccessTokenSecret)

    val tweetFetcher = new UserTweetSearch(twitterClient)
    val tweets = tweetFetcher.fetch("bcn_ajuntament")
    println(tweets.toString())
  }

}
