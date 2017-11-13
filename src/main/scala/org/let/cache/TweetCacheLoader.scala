package org.let.cache

import com.github.benmanes.caffeine.cache.CacheLoader
import org.let.twitter.Tweets.{UserTweets, UserTweetsQuery}
import org.let.twitter.twitter4j.TwitterClient

import scala.collection.JavaConverters._

class TweetCacheLoader(implicit val twitter: TwitterClient) extends CacheLoader[UserTweetsQuery, UserTweets] {

  override def load(key: UserTweetsQuery) = {
    val tweets = twitter.searchUserTweets(key.user, key.tweets).asScala
    UserTweets(key.user, tweets)
  }

}
