package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.Follow;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.FollowServiceProxy;
import edu.byu.cs.client.model.service.LogoutServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.LogoutResponse;
import tweeter.model.service.response.UnfollowResponse;


public class MainBarPresenterTest {


    private LogoutRequest logoutRequest;
    private LogoutResponse logoutResponse;

    private FollowRequest followRequest;
    private FollowResponse followResponse;

    private UnfollowRequest unfollowRequest;
    private UnfollowResponse unfollowResponse;

    private LogoutServiceProxy mockLogoutService;
    private FollowServiceProxy mockFollowService;

    private MainBarPresenter presenter;

    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {

        User currentUser = new User("FirstName", "LastName", null, null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", null);
        Follow follow = new Follow(currentUser, resultUser1);


        logoutRequest = new LogoutRequest(currentUser, new AuthToken());
        followRequest = new FollowRequest(follow);
        unfollowRequest = new UnfollowRequest(follow);

        logoutResponse = new LogoutResponse(true);
        followResponse = new FollowResponse(true);
        unfollowResponse = new UnfollowResponse(true);

        mockLogoutService = Mockito.mock(LogoutServiceProxy.class);
        Mockito.when(mockLogoutService.logout(logoutRequest)).thenReturn(logoutResponse);
        mockFollowService = Mockito.mock(FollowServiceProxy.class);
        Mockito.when(mockFollowService.follow(followRequest)).thenReturn(followResponse);
        Mockito.when(mockFollowService.unfollow(unfollowRequest)).thenReturn(unfollowResponse);

        presenter = Mockito.spy(new MainBarPresenter(new MainBarPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowService);

    }

    @Test
    void testLogin_returnServiceResult() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(logoutResponse, presenter.logout(logoutRequest));
    }

    @Test
    void testFollow_returnServiceResult() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(followResponse, presenter.follow(followRequest));
    }

    @Test
    void testUnfollow_returnServiceResult() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(unfollowResponse, presenter.unfollow(unfollowRequest));
    }


}
