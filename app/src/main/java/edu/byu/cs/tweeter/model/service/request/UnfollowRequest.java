package edu.byu.cs.tweeter.model.service.request;


import edu.byu.cs.tweeter.model.domain.Follow;

public class UnfollowRequest {

    private Follow follow;

    public UnfollowRequest(Follow follow) {
        this.follow = follow;
    }

    public Follow getFollow() {
        return follow;
    }

}
