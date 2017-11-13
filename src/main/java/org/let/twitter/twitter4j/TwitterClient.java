package org.let.twitter.twitter4j;

import org.let.twitter.Tweets;
import org.let.twitter.TwitterSearchException;

import java.util.List;

public interface TwitterClient {
    List<Tweets.Tweet> searchUserTweets(String user, int tweetCount) throws TwitterSearchException;
}
