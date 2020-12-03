package service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.Story;
import tweeter.model.domain.User;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;
import tweeter.server.dao.StoryDAO;
import tweeter.server.service.StoryService;


public class StoryServiceTest {


    private StoryRequest request;
    private StoryResponse response;
    private StoryDAO storyDAOMock;
    private StoryService storyServiceSpy;

    @BeforeEach
    void setup() {

        User currentUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        Status status1 = new Status("statues", null, 0, null, currentUser);
        Status status2 = new Status("statusTEst", null, 0, null, currentUser);
        Status status3 = new Status("StatusTes3", null, 0, null, currentUser);
        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);


        request = new StoryRequest(currentUser, 3, 0);
        response = new StoryResponse(statuses, true, 0);

        storyDAOMock = Mockito.mock(StoryDAO.class);
        Mockito.when(storyDAOMock.getStory(request)).thenReturn(response);

        storyServiceSpy = Mockito.spy(new StoryService());
        Mockito.when(storyServiceSpy.getStoryDAO()).thenReturn(storyDAOMock);

    }


    @Test
    void testStoryService() {
        Assertions.assertEquals(response.getUserStatuses().get(1).getStatus(), storyServiceSpy.getStory(request).getUserStatuses().get(1).getStatus());
    }


}
