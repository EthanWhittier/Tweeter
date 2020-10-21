package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;


public class MainBarPresenterTest {


    private LogoutRequest logoutRequest;
    private LogoutResponse logoutResponse;

    private FollowRequest followRequest;
    private FollowResponse followResponse;

    private UnfollowRequest unfollowRequest;
    private UnfollowResponse unfollowResponse;

    private LogoutService mockLogoutService;
    private FollowService mockFollowService;

    private MainBarPresenter presenter;

    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", null);
        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Follow follow = new Follow(currentUser, resultUser1);


        logoutRequest = new LogoutRequest(currentUser, new AuthToken());
        followRequest = new FollowRequest(follow);
        unfollowRequest = new UnfollowRequest(follow);

        logoutResponse = new LogoutResponse(true);
        followResponse = new FollowResponse(true);
        unfollowResponse = new UnfollowResponse(true);

        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(logoutRequest)).thenReturn(logoutResponse);
        mockFollowService = Mockito.mock(FollowService.class);
        Mockito.when(mockFollowService.follow(followRequest)).thenReturn(followResponse);
        Mockito.when(mockFollowService.unfollow(unfollowRequest)).thenReturn(unfollowResponse);

        presenter = Mockito.spy(new MainBarPresenter(new MainBarPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowService);

    }

    @Test
    void testLogin_returnServiceResult() {
        Assertions.assertEquals(logoutResponse, presenter.logout(logoutRequest));
    }

    @Test
    void testFollow_returnServiceResult() {
        Assertions.assertEquals(followResponse, presenter.follow(followRequest));
    }

    @Test
    void testUnfollow_returnServiceResult() {
        Assertions.assertEquals(unfollowResponse, presenter.unfollow(unfollowRequest));
    }


}
