package edu.byu.cs.client.presenter;


import java.io.IOException;

import edu.byu.cs.client.model.service.FollowersServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;

public class FollowersPresenter {


    private final View view;


    public interface View {}


    public FollowersPresenter(View view) {this.view = view;}

    public FollowersResponse getFollowers(FollowersRequest followersRequest) throws IOException, TweeterRemoteException {
        FollowersServiceProxy followersService = getFollowingService();
        return followersService.getFollowers(followersRequest);
    }


    FollowersServiceProxy getFollowingService() {
        return new FollowersServiceProxy();
    }



}
