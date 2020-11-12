package tweeter.model.service.request;

import tweeter.model.domain.User;

public class FollowersRequest {


    private User folowee;
    private int limit;
    private  User lastFollower;

    public FollowersRequest() {}

    public FollowersRequest(User folowee, int limit, User lastFollower) {
        this.folowee = folowee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public User getFolowee() {
        return folowee;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollower() {
        return lastFollower;
    }
}
