package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.server.dao.StatusDAO;
import tweeter.server.service.FeedService;

public class FeedServiceTest {


    private FeedRequest request;
    private FeedResponse response;
    private StatusDAO statusDAOMock;
    private FeedService feedServiceSpy;


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

        statusDAOMock = Mockito.mock(StatusDAO.class);
        Mockito.when(statusDAOMock.getFeed(request)).thenReturn(response);

        feedServiceSpy = Mockito.spy(new FeedService());
        Mockito.when(feedServiceSpy.getStatusDAO()).thenReturn(statusDAOMock);

    }

    @Test
    void testFeedService() {
        Assertions.assertEquals(response.getStatuses().get(2).getMessage(), feedServiceSpy.getFeed(request).getStatuses().get(2).getMessage());
    }




}
