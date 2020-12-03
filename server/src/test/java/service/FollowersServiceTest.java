package service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import tweeter.model.domain.User;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;
import tweeter.server.dao.FollowerDAO;
import tweeter.server.service.FollowersService;


public class FollowersServiceTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";


    private FollowersRequest request;
    private FollowersResponse response;
    private FollowerDAO followerDAOMock;
    private FollowersService followersServiceSpy;



    @BeforeEach
    void setup() {


        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User resultUser2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
        User resultUser3 = new User("Bob", "Bobson", MALE_IMAGE_URL);


        //request = new FollowersRequest(currentUser, 3, null);
       // response = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        followerDAOMock = Mockito.mock(FollowerDAO.class);
        Mockito.when(followerDAOMock.getFollowers(request)).thenReturn(response);

        followersServiceSpy = Mockito.spy(new FollowersService());
       // Mockito.when(followersServiceSpy.getFollowerDAO()).thenReturn(followerDAOMock);

    }

    @Test
    void testFollowersService() {
        Assertions.assertEquals(response.getFollowers(), followersServiceSpy.getFollowers(request).getFollowers());
    }

}
