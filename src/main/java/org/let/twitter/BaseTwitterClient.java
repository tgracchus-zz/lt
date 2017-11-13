package org.let.twitter;

public abstract class BaseTwitterClient implements TwitterClient {

    protected int calculatePageSize(int maxPageSize, int tweetCount) {
        int pages = (tweetCount / maxPageSize) + 1;
        if (pages == 1) {
            return tweetCount;
        } else {
            if (tweetCount % 2 == 0) {
                return tweetCount / pages;
            } else {
                return (tweetCount / pages) + 1;
            }
        }
    }
}
