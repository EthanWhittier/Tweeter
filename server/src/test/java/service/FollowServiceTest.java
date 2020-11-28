package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import tweeter.model.domain.Follow;
import tweeter.model.domain.User;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;
import tweeter.server.dao.FollowDAO;
import tweeter.server.service.FollowService;

public class FollowServiceTest {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private FollowRequest request;
    private FollowResponse response;
    private FollowDAO followDAOMock;
    private FollowService followServiceSpy;

    private UnfollowRequest unfollowRequest;
    private UnfollowResponse unfollowResponse;



    @BeforeEach
    void setup() {

        User resultUser1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User resultUser2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);

        Follow follow = new Follow(resultUser1, resultUser2);

        request = new FollowRequest(follow);
        response = new FollowResponse(true, "Follow Successful");

        unfollowRequest = new UnfollowRequest(follow);
        unfollowResponse = new UnfollowResponse(true, "Unfollow Successful");

        followDAOMock = Mockito.mock(FollowDAO.class);
        Mockito.when(followDAOMock.follow(request)).thenReturn(response);
        Mockito.when(followDAOMock.unfollow(unfollowRequest)).thenReturn(unfollowResponse);

        followServiceSpy = Mockito.spy(new FollowService());
        Mockito.when(followServiceSpy.getFollowDAO()).thenReturn(followDAOMock);

    }

    @Test
    void testFollowService_Follow() {
        Assertions.assertEquals(response.isSuccess(), followServiceSpy.follow(request).isSuccess());
    }


    @Test
    void testFollowService_Unfollow() {
        Assertions.assertEquals(unfollowResponse.isSuccess(), followServiceSpy.unfollow(unfollowRequest).isSuccess());
    }
}
