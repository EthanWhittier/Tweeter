package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.FollowersServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;


public class FollowerPresenterTest {



    private FollowersRequest request;
    private FollowersResponse response;
    private FollowersServiceProxy mockFollowerService;
    private FollowersPresenter presenter;


    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", null);
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", null);
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png", null);

        request = new FollowersRequest(currentUser, 10, null);
        response = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        mockFollowerService = Mockito.mock(FollowersServiceProxy.class);
        Mockito.when(mockFollowerService.getFollowers(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowersPresenter(new FollowersPresenter.View() {}));
        Mockito.when(presenter.getFollowingService()).thenReturn(mockFollowerService);
    }


    @Test
    public void testGetFollowers_returnsServiceResult() throws IOException, TweeterRemoteException {
       Assertions.assertEquals(response, presenter.getFollowers(request));
    }

    @Test
    public void testGetFollowers_serviceThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFollowerService.getFollowers(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowers(request);
        });
    }

}
