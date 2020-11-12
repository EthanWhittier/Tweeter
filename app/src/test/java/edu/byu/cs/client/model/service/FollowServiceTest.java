package edu.byu.cs.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import tweeter.model.domain.Follow;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.UnfollowResponse;

public class FollowServiceTest {




    private FollowRequest followRequest;
    private UnfollowRequest unfollowRequest;

    private FollowResponse followResponse;
    private UnfollowResponse unfollowResponse;

    private ServerFacade mockServerFacade;

    private FollowServiceProxy followService;

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

        followService = new FollowServiceProxy();
    }

    @Test
    void testFollow_returnsServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(followResponse.isSuccess(), followService.follow(followRequest).isSuccess());
    }

    @Test
    void testUnfollow_returnServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(unfollowResponse.isSuccess(), followService.unfollow(unfollowRequest).isSuccess());
    }




}
