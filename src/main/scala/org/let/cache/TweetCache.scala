package org.let.cache

import org.let.twitter.Tweets.{UserTweets, UserTweetsQuery}

class TweetCache(val readThroughCache: ReadThroughCache[UserTweetsQuery, UserTweets]) extends ReadThroughCache[UserTweetsQuery, UserTweets] {
  override def getOrLoad(key: UserTweetsQuery) = readThroughCache.getOrLoad(key)

}


