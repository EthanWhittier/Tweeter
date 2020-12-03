package tweeter.model.service.request;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.response.FeedResponse;

public class FeedRequest {

    private User user;
    private int limit;
    private  long lastCreatedFeed;
    private String lastUserFeed;

    public FeedRequest() {}

    public FeedRequest(User user, int limit, long lastCreatedFeed, String lastUserFeed) {
        this.user = user;
        this.limit = limit;
        this.lastCreatedFeed = lastCreatedFeed;
        this.lastUserFeed = lastUserFeed;
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getLastCreatedFeed() {
        return lastCreatedFeed;
    }

    public void setLastCreatedFeed(long lastCreatedFeed) {
        this.lastCreatedFeed = lastCreatedFeed;
    }

    public String getLastUserFeed() {
        return lastUserFeed;
    }

    public void setLastUserFeed(String lastUserFeed) {
        this.lastUserFeed = lastUserFeed;
    }
}
