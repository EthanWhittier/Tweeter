package edu.byu.cs.tweeter.model.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

public class FollowerServiceTest {


    private FollowersRequest validRequest;
    private FollowersResponse successResponse;

    private FollowersRequest invalidRequest;
    private FollowersResponse failResponse;

    private FollowersService followersServiceSpy;
    private ServerFacade mockServerFacade;

    @BeforeEach
    void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        validRequest = new FollowersRequest(currentUser, 3, null);
        invalidRequest = new FollowersRequest(null, 0, null);

        successResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        mockServerFacade = Mockito.mock(ServerFacade.class);

        failResponse = new FollowersResponse("Fail");

        followersServiceSpy = Mockito.spy(new FollowersService());
        Mockito.when(followersServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
        Mockito.when(mockServerFacade.getFollowers(validRequest)).thenReturn(successResponse);
    }

    @Test
    void testGetFollowers_validRequest_validResponse() throws IOException {
        Assertions.assertEquals(successResponse, followersServiceSpy.getFollowers(validRequest));
    }

    @Test
    public void testGetFollowers_validRequest_loadProfilePictures() throws IOException {
        FollowersResponse response = followersServiceSpy.getFollowers(validRequest);

        for(User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }



}
