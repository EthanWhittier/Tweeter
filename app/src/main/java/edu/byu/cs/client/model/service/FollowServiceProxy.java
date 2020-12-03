package edu.byu.cs.client.model.service;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import edu.byu.cs.client.util.ByteArrayUtils;
import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.FollowServiceInterface;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.GetUserRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.GetUserResponse;
import tweeter.model.service.response.UnfollowResponse;

public class FollowServiceProxy implements FollowServiceInterface {

    private final String FOLLOW_URL = "follow";
    private final String UNFOLLOW_URL = "unfollow";
    private final String GETUSER_URL = "getuser";

    public FollowResponse follow(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.follow(followRequest, FOLLOW_URL);
    }


    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.unfollow(unfollowRequest, UNFOLLOW_URL);
    }

    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        GetUserResponse response = getServerFacade().getUser(request, GETUSER_URL);
        if(response.isSuccess()) {
            loadImage(response.getUser());
        }
       return response;
    }


    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getAlias());
        user.setImageBytes(bytes);
    }



    public ServerFacade getServerFacade() {
        return new ServerFacade();
    }





}
