package org.let.twitter.twitter4j;

import org.let.twitter.TwitterSearchException;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Twitter4JClient implements TwitterClient {

    private final Twitter twitter;
    private final Twitter4JPaginator twitter4JPaginator;
    private final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    protected Twitter4JClient(Twitter twitter, Twitter4JPaginator twitter4JPaginator) {
        this.twitter = twitter;
        this.twitter4JPaginator = twitter4JPaginator;
    }

    public List<JTweet> searchUserTweets(String user, int tweetCount) throws TwitterSearchException {
        List<JTweet> statusList = new ArrayList<>();
        Query query = new Query("from:" + user);
        query.setCount(twitter4JPaginator.calculatePageSize(100, tweetCount));
        QueryResult queryResult;
        do {

            try {
                queryResult = twitter.search(query);
            } catch (TwitterException e) {
                throw new TwitterSearchException(e);
            }

            for (Status tweet : queryResult.getTweets()) {
                statusList.add(new JTweet(tweet.getText(), df.format(tweet.getCreatedAt())));
            }
            query = queryResult.nextQuery();

        } while (statusList.size() < tweetCount);

        List<JTweet> trimStatusList = new ArrayList<>();
        for (int i = 0, j = 0; i < tweetCount && j < statusList.size(); i++, j++) {
            trimStatusList.add(statusList.get(i));
        }

        return trimStatusList;
    }


    public static TwitterClient newTwitterClient(String oAuthConsumerKey, String oAuthConsumerSecret,
                                                 String oAuthAccessToken, String oAuthAccessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(oAuthConsumerKey)
                .setOAuthConsumerSecret(oAuthConsumerSecret)
                .setOAuthAccessToken(oAuthAccessToken)
                .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        twitter4j.TwitterFactory tf = new twitter4j.TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return new Twitter4JClient(twitter, new Twitter4JPaginator());
    }

}
