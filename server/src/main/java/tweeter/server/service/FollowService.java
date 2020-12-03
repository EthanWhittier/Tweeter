package tweeter.server.service;

import java.io.IOException;

import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.FollowServiceInterface;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.GetUserRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.GetUserResponse;
import tweeter.model.service.response.UnfollowResponse;
import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.UserDAO;
import tweeter.server.factory.FactoryManager;

public class FollowService implements FollowServiceInterface {

    @Override
    public FollowResponse follow(FollowRequest request) {
        return getFollowDAO().follow(request);
    }

    @Override
    public UnfollowResponse unfollow(UnfollowRequest request) {
        return getFollowDAO().unfollow(request);
    }

    public GetUserResponse getUser(GetUserRequest request) {
        User user = getUserDAO().getUser(request.getUser());
        boolean isFollowing = getFollowDAO().isFollowing(request.getLoggedInUser(), user.getAlias());
        return new GetUserResponse(user, isFollowing);
    }


    public FollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    public UserDAO getUserDAO() {return FactoryManager.getDAOFactory().makeUserDAO();}


}
