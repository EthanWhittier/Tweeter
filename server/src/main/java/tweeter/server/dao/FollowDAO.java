package tweeter.server.dao;

import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;

public class FollowDAO {


    public FollowResponse follow(FollowRequest request) {
        return new FollowResponse(true, "Follow Successful");
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        return new UnfollowResponse(true, "Unfollow Successful");
    }


}
