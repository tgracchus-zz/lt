package org.let.twitter.twitter4j;

import org.let.twitter.BaseTwitterClient;
import org.let.twitter.TwitterClient;
import org.let.twitter.TwitterSearchException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class Twitter4JClient extends BaseTwitterClient {

    private final Twitter twitter;

    public Twitter4JClient(Twitter twitter) {
        this.twitter = twitter;
    }

    public List<Status> searchUserTweets(String user, int tweetCount) throws TwitterSearchException {
        List<Status> statusList = new ArrayList<>();
        Query query = new Query("from:" + user);
        query.setCount(calculatePageSize(100, tweetCount));
        QueryResult queryResult;
        do {

            try {
                queryResult = twitter.search(query);
            } catch (TwitterException e) {
                throw new TwitterSearchException(e);
            }
            statusList.addAll(queryResult.getTweets());
            query = queryResult.nextQuery();

        } while (statusList.size() < tweetCount);

        List<Status> trimStatusList = new ArrayList<>();
        for (int i = 0, j = 0; i < tweetCount && j < statusList.size(); i++, j++) {
            trimStatusList.add(statusList.get(i));
        }

        return trimStatusList;
    }


    public static TwitterClient newTwitterClient(boolean debug, String oAuthConsumerKey, String oAuthConsumerSecret,
                                                 String oAuthAccessToken, String oAuthAccessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(debug)
                .setOAuthConsumerKey(oAuthConsumerKey)
                .setOAuthConsumerSecret(oAuthConsumerSecret)
                .setOAuthAccessToken(oAuthAccessToken)
                .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        twitter4j.TwitterFactory tf = new twitter4j.TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return new Twitter4JClient(twitter);
    }

}
