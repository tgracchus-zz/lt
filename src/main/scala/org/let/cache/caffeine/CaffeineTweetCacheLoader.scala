package org.let.cache.caffeine

import java.text.SimpleDateFormat

import com.github.benmanes.caffeine.cache.CacheLoader
import org.let.twitter.Tweets.{Tweet, UserTweets, UserTweetsQuery}
import org.let.twitter.TwitterClient

import scala.collection.JavaConverters._

class CaffeineTweetCacheLoader(implicit val twitter: TwitterClient) extends CacheLoader[UserTweetsQuery, UserTweets] {
  private val df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")

  override def load(key: UserTweetsQuery) = {
    val tweets = twitter.searchUserTweets(key.user, key.tweets)
      .asScala.map(tweet => {
      new Tweet(tweet = tweet.getText, createdAt = df.format(tweet.getCreatedAt))
    })

    UserTweets(key.user, tweets)
  }

}
