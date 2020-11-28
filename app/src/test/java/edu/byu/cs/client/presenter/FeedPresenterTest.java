package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Feed;
import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.FeedServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;

public class FeedPresenterTest {


    private FeedRequest request;
    private FeedResponse response;
    private FeedServiceProxy mockFeedService;
    private FeedPresenter presenter;

    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", null);
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status("status", null, null, null, currentUser));

        request = new FeedRequest(currentUser, 10, null);
        response = new FeedResponse(statuses, true);

        mockFeedService = Mockito.mock(FeedServiceProxy.class);
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);

    }

    @Test
    void testgetFeed_returnServiceResult() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response, presenter.getFeed(request));
    }



}
