package org.let.twitter;

import java.util.List;

public interface TwitterClient {
    List<JTweet> searchUserTweets(String user, int tweetCount) throws TwitterSearchException;
}
