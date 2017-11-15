package org.let.cache

import com.github.benmanes.caffeine.cache.CacheLoader
import org.let.twitter.TweetsActor.{Tweet, UserTweets, UserTweetsQuery}
import org.let.twitter.TwitterClient

import scala.collection.JavaConverters._

class TweetCacheLoader(implicit val twitter: TwitterClient) extends CacheLoader[UserTweetsQuery, UserTweets] {

  override def load(key: UserTweetsQuery) = {
    val tweets = twitter.searchUserTweets(key.user, key.tweets)
      .asScala
      .map(jtweet => new Tweet(jtweet.getTweet, jtweet.getCreatedAt))
    UserTweets(key.user, tweets)
  }

}
