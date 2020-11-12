package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;

public interface FollowersServiceInterface {

    FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException;

}
