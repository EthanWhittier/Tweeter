package edu.byu.cs.client.model.service;

import java.io.IOException;

import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.FollowersServiceInterface;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

public class FollowersServiceProxy implements FollowersServiceInterface {

    private final String GET_FOLLOWERS_URL = "/getfollowers";

    public FollowersResponse getFollowers(FollowersRequest followersRequest) throws IOException, TweeterRemoteException {
        FollowersResponse followersResponse = getServerFacade().getFollowers(followersRequest, GET_FOLLOWERS_URL);
        if(followersResponse.isSuccess()) {
            loadImages(followersResponse);
        }
        return followersResponse;
    }


    private void loadImages(FollowersResponse response) throws IOException {

        for(User user : response.getFollowers()) {
           try {
               byte[] bytes = ByteArrayUtils.bytesFromUrl(user.getAlias());
               user.setImageBytes(bytes);
           } catch (Exception e) {
               byte[] bytes = ByteArrayUtils.bytesFromUrl("eiwhitt"); //DUMMY IMAGE
               user.setImageBytes(bytes);
           }
        }
    }




    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
