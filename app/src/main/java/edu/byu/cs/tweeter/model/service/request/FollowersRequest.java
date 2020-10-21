package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersRequest {


    private final User folowee;
    private final int limit;
    private final User lastFollower;

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
