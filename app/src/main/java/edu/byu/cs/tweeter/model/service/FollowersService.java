package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class FollowersService {



    public FollowersResponse getFollowers(FollowersRequest followersRequest) throws IOException {
        FollowersResponse followersResponse = getServerFacade().getFollowers(followersRequest);
        if(followersResponse.isSuccess()) {
            loadImages(followersResponse);
        }
        return followersResponse;
    }


    private void loadImages(FollowersResponse response) throws IOException {
        for(User user : response.getFollowers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }




    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
