package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenterTest {


    private FeedRequest request;
    private FeedResponse response;
    private FeedService mockFeedService;
    private FeedPresenter presenter;

    @BeforeEach
    void setup() throws IOException {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status("status", null, null, null, currentUser));

        request = new FeedRequest(currentUser, 10, null);
        response = new FeedResponse(new Feed(currentUser, statuses), true);

        mockFeedService = Mockito.mock(FeedService.class);
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);

    }

    @Test
    void testgetFeed_returnServiceResult() throws IOException {
        Assertions.assertEquals(response, presenter.getFeed(request));
    }



}
