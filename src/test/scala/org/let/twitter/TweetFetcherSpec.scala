package org.let.twitter

import org.scalatest.{FlatSpec, Matchers}

class TweetFetcherSpec extends FlatSpec with Matchers {

  "TweetFetcher" should "retrieve my tweets" in {
    val tweetFetcher = new TweetFetcher(TwitterClientFactory.newUlisesTwitter())
   val tweets = tweetFetcher.fetch("bcn_ajuntament")
    println(tweets.toString())
  }

}
