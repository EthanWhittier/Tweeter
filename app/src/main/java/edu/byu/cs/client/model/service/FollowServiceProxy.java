package edu.byu.cs.client.model.service;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.FollowServiceInterface;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;

public class FollowServiceProxy implements FollowServiceInterface {

    private final String FOLLOW_URL = "/follow";
    private final String UNFOLLOW_URL = "unfollow";

    public FollowResponse follow(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.follow(followRequest, FOLLOW_URL);
    }


    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.unfollow(unfollowRequest, UNFOLLOW_URL);
    }


    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }





}
