package edu.byu.cs.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;

public class StoryServiceTest {



    private StoryRequest request;
    private StoryResponse response;
    private StoryServiceProxy storyService;

    @BeforeEach
    void setup() {

        User currentUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", null);
        Status status1 = new Status("statues", null, "11/4/2333", null, currentUser);
        Status status2 = new Status("statusTEst", null, "99/23/3330", null, currentUser);
        Status status3 = new Status("StatusTes3", null, "52/29/3040", null, currentUser);
        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);

        request = new StoryRequest(currentUser, 3, null);
        response = new StoryResponse(statuses, true);

        storyService = new StoryServiceProxy();

    }

    @Test
    void testGetStory_returnsServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response.getUserStatuses().get(1).getMessage(), storyService.getStory(request).getUserStatuses().get(1).getMessage());
    }


}
