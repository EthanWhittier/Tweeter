package tweeter.model.service.request;


import tweeter.model.domain.Follow;

public class UnfollowRequest {

    public UnfollowRequest() {}

    private String follower;
    private String followee;

    public UnfollowRequest(String follower, String followee) {
        this.follower = follower;
        this.followee = followee;
    }


    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }
}
