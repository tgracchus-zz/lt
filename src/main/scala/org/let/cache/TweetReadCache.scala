package org.let.cache

import org.let.twitter.Tweets.{UserTweets, UserTweetsQuery}

trait TweetReadCache extends ReadCache[UserTweetsQuery, UserTweets] {
  implicit val readCache: ReadCache[UserTweetsQuery, UserTweets]
  override def getOrLoad(key: UserTweetsQuery): UserTweets = readCache.getOrLoad(key)
}
