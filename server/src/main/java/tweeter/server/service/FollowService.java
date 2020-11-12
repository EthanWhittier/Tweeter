package tweeter.server.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.FollowServiceInterface;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;
import tweeter.server.dao.FollowDAO;

public class FollowService implements FollowServiceInterface {

    @Override
    public FollowResponse follow(FollowRequest request) {
        return getFollowDAO().follow(request);
    }

    @Override
    public UnfollowResponse unfollow(UnfollowRequest request) {
        return getFollowDAO().unfollow(request);
    }

    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

}
