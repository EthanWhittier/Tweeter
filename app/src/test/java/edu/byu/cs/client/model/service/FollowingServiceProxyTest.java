package edu.byu.cs.client.model.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.response.FollowersResponse;
import tweeter.model.service.response.FollowingResponse;

public class FollowingServiceProxyTest {


    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";


    private FollowingRequest validRequest;
    private FollowingResponse successResponse;

    private FollowingRequest invalidRequest;
    private FollowingResponse failResponse;

    private FollowingServiceProxy followingService;
    private ServerFacade mockServerFacade;

    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, null);

        User resultUser1 = new User("Allen", "Anderson", MALE_IMAGE_URL, null);
        User resultUser2 = new User("Amy", "Ames", FEMALE_IMAGE_URL, null);
        User resultUser3 = new User("Bob", "Bobson", MALE_IMAGE_URL, null);

        validRequest = new FollowingRequest(currentUser, 3, null);

        successResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        followingService = new FollowingServiceProxy();

    }

    @Test
    void testGetFollowers_validRequest_validResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(successResponse.getFollowees(), followingService.getFollowees(validRequest).getFollowees());
    }

    @Test
    public void testGetFollowers_validRequest_loadProfilePictures() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingService.getFollowees(validRequest);

        for(User user : response.getFollowees()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }



}
