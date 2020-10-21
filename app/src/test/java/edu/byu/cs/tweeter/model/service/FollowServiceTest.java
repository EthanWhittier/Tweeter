package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class FollowServiceTest {




    private FollowRequest followRequest;
    private UnfollowRequest unfollowRequest;

    private FollowResponse followResponse;
    private UnfollowResponse unfollowResponse;

    private ServerFacade mockServerFacade;

    private FollowService followServiceSpy;

    private User user1;
    private User user2;


    @BeforeEach
    void setup() {

        user1 = new User("Daffy", "Duck", "");
        user2 = new User("Fred", "Flintstone", "");


        followRequest = new FollowRequest(new Follow(user1, user2));
        unfollowRequest = new UnfollowRequest(new Follow(user1, user2));

        followResponse = new FollowResponse(true);
        unfollowResponse = new UnfollowResponse(true);

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.follow(followRequest)).thenReturn(followResponse);
        Mockito.when(mockServerFacade.unfollow(unfollowRequest)).thenReturn(unfollowResponse);

        followServiceSpy = Mockito.spy(new FollowService());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    @Test
    void testFollow_returnsServerFacadeResponse() {
        Assertions.assertEquals(followResponse, followServiceSpy.follow(followRequest));
    }

    @Test
    void testUnfollow_returnServerFacadeResponse() {
        Assertions.assertEquals(unfollowResponse, followServiceSpy.unfollow(unfollowRequest));
    }




}
