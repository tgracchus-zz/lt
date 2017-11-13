package org.let.twitter.twitter4j;

public class Twitter4JPaginator {

    public int calculatePageSize(int maxPageSize, int tweetCount) {
        int pages = (tweetCount / maxPageSize);
        int rest = (tweetCount % maxPageSize);
        int elemPerPages;
        if (rest == 0) {
            elemPerPages = maxPageSize;
        } else {
            elemPerPages = (int) Math.ceil(tweetCount / (double) (pages + 1));
        }
        return elemPerPages;
    }
}
