package org.let.twitter.twitter4j;

import org.let.twitter.TwitterSearchException;

import java.util.List;

public interface TwitterClient {
    List<JTweet> searchUserTweets(String user, int tweetCount) throws TwitterSearchException;
}
