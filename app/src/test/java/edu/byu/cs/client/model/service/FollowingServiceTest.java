package edu.byu.cs.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.response.FollowersResponse;

public class FollowingServiceTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private FollowersRequest validRequest;
    private FollowersResponse successResponse;
    private FollowersServiceProxy followersService;
    private final String GET_FOLLOWING_URL = "/getfollowers";

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User resultUser2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
        User resultUser3 = new User("Bob", "Bobson", MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new FollowersRequest(currentUser, 3, null);
        successResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        followersService = new FollowersServiceProxy();
    }

    /**
     * Verify that for successful requests the {@link FollowingServiceProxy#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(successResponse.getFollowers(), followersService.getFollowers(validRequest).getFollowers());
    }

    /**
     * Verify that the {@link FollowingServiceProxy#getFollowees(FollowingRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowees_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersService.getFollowers(validRequest);

        for (User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }



}
