package org.let.twitter;

import twitter4j.Status;

import java.util.List;

public interface TwitterClient {
    List<Status> searchUserTweets(String user, int tweetCount) throws TwitterSearchException;
}
