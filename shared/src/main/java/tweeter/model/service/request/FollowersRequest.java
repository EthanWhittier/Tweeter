package tweeter.model.service.request;

import tweeter.model.domain.User;

public class FollowersRequest {


    private String followee;
    private int limit;
    private  String lastFollower;

    public FollowersRequest() {}

    public FollowersRequest(String followee, int limit, String lastFollower) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollower() {
        return lastFollower;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }
}

