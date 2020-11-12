package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.response.FollowingResponse;

public interface FollowingServiceInterface {


    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    FollowingResponse getFollowees(FollowingRequest request)
            throws IOException, TweeterRemoteException;

}
