package tweeter.server.service;

import tweeter.model.service.FollowersServiceInterface;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;
import tweeter.server.dao.FollowerDAO;

public class FollowersService implements FollowersServiceInterface {

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getFollowerDAO().getFollowers(request);
    }

    public FollowerDAO getFollowerDAO() {
        return new FollowerDAO();
    }



}
