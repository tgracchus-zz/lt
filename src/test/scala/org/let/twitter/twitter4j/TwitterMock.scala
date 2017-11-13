package org.let.twitter.twitter4j


import java.util.Date

import org.let.cache.caffeine.CaffeineReadCache
import org.let.cache.{ReadCache, TweetCacheLoader}
import org.let.twitter.Tweets.{UserTweets, UserTweetsQuery}
import org.mockito.Matchers.anyObject
import org.mockito.Mockito.when
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.scalatest.mockito.MockitoSugar
import twitter4j._

import scala.collection.JavaConverters._


trait TwitterMock extends MockitoSugar {

  val twitter4j: Twitter = {
    val twitter = mock[Twitter]
    val result = mock[QueryResult]

    when(result.nextQuery()).thenReturn(new Query())

    val status = 1 to 200 map (i => {
      val tweet = mock[Status]
      when(tweet.getCreatedAt).thenReturn(new Date(1510677467))
      when(tweet.getText).thenReturn("tweet" + i)
      tweet
    })

    when(result.getTweets).thenReturn(status.asJava)
    when(twitter.search(anyObject())).thenAnswer(new Answer[QueryResult] {
      override def answer(invocation: InvocationOnMock): QueryResult = {
        val query = invocation.getArgumentAt(0, classOf[Query])
        if (query.getQuery == "from:error") {
          throw new TwitterException(new Exception("mockError"))
        } else {
          return result
        }
      }
    })
    twitter
  }

  implicit val twitter: TwitterClient = {
    new Twitter4JClient(twitter4j, new Twitter4JPaginator)
  }

  val cacheLoader = new TweetCacheLoader()
  implicit val readCache: ReadCache[UserTweetsQuery, UserTweets] = new CaffeineReadCache[UserTweetsQuery, UserTweets](cacheLoader)

}
