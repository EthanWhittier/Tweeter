package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;

public interface FollowServiceInterface {


    FollowResponse follow(FollowRequest request) throws IOException, TweeterRemoteException;

    UnfollowResponse unfollow(UnfollowRequest request) throws IOException, TweeterRemoteException;

}
