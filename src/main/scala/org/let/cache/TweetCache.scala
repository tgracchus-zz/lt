package org.let.cache

import org.let.cache.caffeine.CaffeineReadCache
import org.let.twitter.TweetsActor.{UserTweets, UserTweetsQuery}
import org.let.twitter.TwitterClient

trait TweetCache {
  val cacheLoader: TweetCacheLoader = new TweetCacheLoader()
  implicit val tweetCache: ReadCache[UserTweetsQuery, UserTweets] = new CaffeineReadCache[UserTweetsQuery, UserTweets](cacheLoader)
  implicit val twitter: TwitterClient

}
