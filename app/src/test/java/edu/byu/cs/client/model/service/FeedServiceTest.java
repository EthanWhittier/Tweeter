package edu.byu.cs.client.model.service;

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
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;

public class FeedServiceTest {



    private FeedRequest request;
    private FeedResponse response;
    private FeedServiceProxy feedService;


    @BeforeEach
    void setup() {

        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Status status1 = new Status("Hello", null, "10/4/2393", null, user);
        Status status2 = new Status("Test", null, "39/23/3930", null, user);
        Status status3 = new Status("Test2", null, "58/29/3940", null, user);
        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);

        request = new FeedRequest(user, 3, null);
        response = new FeedResponse(statuses, true);



        feedService = new FeedServiceProxy();

    }

    @Test
    void testGetFeed_returnsServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response.getStatuses().get(2).getMessage(), feedService.getFeed(request).getStatuses().get(2).getMessage());
    }

    @Test
    void testGetFeed_badRequest() {
        Assertions.assertThrows(TweeterRemoteException.class, () -> {feedService.getFeed(null);});
    }



}
