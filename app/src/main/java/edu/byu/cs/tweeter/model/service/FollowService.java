package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class FollowService {


    public FollowResponse follow(FollowRequest followRequest) {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.follow(followRequest);
    }


    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.unfollow(unfollowRequest);
    }



    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }





}
