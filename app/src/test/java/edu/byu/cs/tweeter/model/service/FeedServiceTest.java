package edu.byu.cs.tweeter.model.service;

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
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedServiceTest {



    private FeedRequest request;
    private FeedResponse response;
    private ServerFacade mockServerFacade;
    private FeedService feedServiceSpy;


    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status("status", null, null, null, currentUser));

        request = new FeedRequest(currentUser, 10, null);
        response = new FeedResponse(new Feed(currentUser, statuses), true);

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(request)).thenReturn(response);

        feedServiceSpy = Mockito.spy(new FeedService());
        Mockito.when(feedServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    void testGetFeed_returnsServerFacadeResponse() throws IOException {
        Assertions.assertEquals(response, feedServiceSpy.getFeed(request));
    }



}
