package org.let.twitter

import twitter4j.{Query, QueryResult, Twitter}

import scala.collection.JavaConverters._

class TweetFetcher(val twitter: Twitter) {

  def fetch(user : String): List[String] = {
    val query = new Query("from:"+user)
    val queryResult: QueryResult = twitter.search(query)
    (queryResult.getTweets() asScala).toList.map(status => status.getText)
  }

}
