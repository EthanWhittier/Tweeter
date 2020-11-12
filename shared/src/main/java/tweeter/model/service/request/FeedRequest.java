package tweeter.model.service.request;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.response.FeedResponse;

public class FeedRequest {

    private User user;
    private int limit;
    private  Status lastStatus;

    public FeedRequest() {}

    public FeedRequest(User user, int limit, Status lastStatus) {
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

}
