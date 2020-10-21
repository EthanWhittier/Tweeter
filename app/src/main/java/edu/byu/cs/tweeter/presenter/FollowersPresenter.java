package edu.byu.cs.tweeter.presenter;

import android.view.View;


import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowersService;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;

public class FollowersPresenter {


    private final View view;


    public interface View {}


    public FollowersPresenter(View view) {this.view = view;}

    public FollowersResponse getFollowers(FollowersRequest followersRequest) throws IOException {
        FollowersService followersService = getFollowingService();
        return followersService.getFollowers(followersRequest);
    }


    FollowersService getFollowingService() {
        return new FollowersService();
    }



}
