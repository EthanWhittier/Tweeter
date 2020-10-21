package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowersService;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;


public class FollowerPresenterTest {



    private FollowersRequest request;
    private FollowersResponse response;
    private FollowersService mockFollowerService;
    private FollowersPresenter presenter;


    @BeforeEach
    void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        request = new FollowersRequest(currentUser, 10, null);
        response = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);

        mockFollowerService = Mockito.mock(FollowersService.class);
        Mockito.when(mockFollowerService.getFollowers(request)).thenReturn(response);

        presenter = Mockito.spy(new FollowersPresenter(new FollowersPresenter.View() {}));
        Mockito.when(presenter.getFollowingService()).thenReturn(mockFollowerService);
    }


    @Test
    public void testGetFollowers_returnsServiceResult() throws IOException {
       Assertions.assertEquals(response, presenter.getFollowers(request));
    }

    @Test
    public void testGetFollowers_serviceThrowsIOException() throws IOException {
        Mockito.when(mockFollowerService.getFollowers(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFollowers(request);
        });
    }

}
