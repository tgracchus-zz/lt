package org.let.cache.caffeine

import org.let.cache.TweetCache
import org.let.twitter.TweetsActor.UserTweetsQuery
import org.let.twitter.twitter4j.TwitterMock
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}


class TweetCacheLoaderSpec extends FlatSpec with Matchers with TweetCache with TwitterMock with BeforeAndAfterAll {

  "TweetCacheLoader" should "get user 10 tweets" in {
    val userTweetsQuery = UserTweetsQuery("user", 10)
    val tweets = cacheLoader.load(userTweetsQuery)
    assert(tweets.tweets.size == 10)
    assert(tweets.user == "user")
    tweets.tweets.view.zipWithIndex.forall(tweet => tweet._1.tweet == "tweet" + tweet._2)
  }

  "TweetCacheLoader" should "get user 102 tweets" in {
    val userTweetsQuery = UserTweetsQuery("user", 102)
    val tweets = cacheLoader.load(userTweetsQuery)
    assert(tweets.tweets.size == 102)
    assert(tweets.user == "user")
    tweets.tweets.view.zipWithIndex.forall(tweet => tweet._1.tweet == "tweet" + tweet._2)
  }

}
