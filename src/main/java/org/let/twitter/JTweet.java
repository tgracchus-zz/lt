package org.let.twitter;

public class JTweet {
    private final String tweet;
    private final String createdAt;

    public JTweet(String tweet, String createdAt) {
        this.tweet = tweet;
        this.createdAt = createdAt;
    }

    public String getTweet() {
        return tweet;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
