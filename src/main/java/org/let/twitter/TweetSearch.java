package org.let.twitter;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class TweetSearch {

    public static List<Status> userTweetSearch(Twitter twitter, String user, int tweetCount) throws TwitterException {
        List<Status> statusList = new ArrayList<>();
        Query query = new Query("from:" + user);
        query.setCount(calculatePageSize(100, tweetCount));
        QueryResult queryResult;
        do {

            queryResult = twitter.search(query);
            statusList.addAll(queryResult.getTweets());
            query = queryResult.nextQuery();

        } while (statusList.size() < tweetCount);

        List<Status> trimStatusList = new ArrayList<>();
        for (int i = 0, j = 0; i < tweetCount && j < statusList.size(); i++, j++) {
            trimStatusList.add(statusList.get(i));
        }

        return trimStatusList;
    }

    protected static int calculatePageSize(int maxPageSize, int tweetCount) {
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
