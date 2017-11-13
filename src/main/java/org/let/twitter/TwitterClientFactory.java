package org.let.twitter;

import twitter4j.Twitter;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClientFactory {

    public static Twitter newTwitter(boolean debug, String oAuthConsumerKey, String oAuthConsumerSecret,
                                     String oAuthAccessToken, String oAuthAccessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(debug)
                .setOAuthConsumerKey(oAuthConsumerKey)
                .setOAuthConsumerSecret(oAuthConsumerSecret)
                .setOAuthAccessToken(oAuthAccessToken)
                .setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        twitter4j.TwitterFactory tf = new twitter4j.TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

}
