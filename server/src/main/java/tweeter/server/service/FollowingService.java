package tweeter.server.service;

import tweeter.model.service.FollowingServiceInterface;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.response.FollowingResponse;
import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.FollowingDAO;
import tweeter.server.factory.FactoryManager;

public class FollowingService implements FollowingServiceInterface {


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowingDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        return getFollowDAO().getFolloweesOfUser(request);
    }

    /**
     * Returns an instance of {@link FollowingDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    public FollowDAO getFollowDAO() {
        return FactoryManager.getDAOFactory().makeFollowDAO();
    }
}
