package tweeter.model.service.request;


import tweeter.model.domain.Follow;

public class UnfollowRequest {

    public UnfollowRequest() {}

    private Follow follow;

    public UnfollowRequest(Follow follow) {
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }

}
