package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import tweeter.model.domain.User;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.response.FollowingResponse;
import tweeter.server.dao.FollowingDAO;
import tweeter.server.service.FollowingService;

public class FollowingServiceTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private FollowingRequest request;
    private FollowingResponse response;
    private FollowingDAO followingDAOMock;
    private FollowingService followingServiceSpy;


    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User resultUser2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
        User resultUser3 = new User("Bob", "Bobson", MALE_IMAGE_URL);

        request = new FollowingRequest(currentUser, 3, null);
        response = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        followingDAOMock = Mockito.mock(FollowingDAO.class);
        Mockito.when(followingDAOMock.getFollowees(request)).thenReturn(response);

        followingServiceSpy = Mockito.spy(new FollowingService());
        Mockito.when(followingServiceSpy.getFollowingDAO()).thenReturn(followingDAOMock);


    }


    @Test
    void testSucessGetFollowingUser() {
        Assertions.assertEquals(response, followingServiceSpy.getFollowees(request));
    }


}
