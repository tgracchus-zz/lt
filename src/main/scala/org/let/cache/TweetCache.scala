package org.let.cache

import org.let.cache.caffeine.CaffeineReadCache
import org.let.twitter.TweetsActor.{UserTweets, UserTweetsQuery}
import org.let.twitter.twitter4j.{Twitter4JClient, TwitterClient}
import twitter4j.Twitter

trait TweetCache {
  val cacheLoader: TweetCacheLoader = new TweetCacheLoader()
  implicit val tweetCache: ReadCache[UserTweetsQuery, UserTweets] = new CaffeineReadCache[UserTweetsQuery, UserTweets](cacheLoader)
  implicit val twitter: TwitterClient

}
