package tweeter.server.service;

import tweeter.model.service.FollowersServiceInterface;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;
import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.FollowerDAO;
import tweeter.server.factory.FactoryManager;

public class FollowersService implements FollowersServiceInterface {

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getFollowerDAO().getFollowersOfUser(request);
    }

    public FollowDAO getFollowerDAO() {
       return FactoryManager.getDAOFactory().makeFollowDAO();
    }



}
