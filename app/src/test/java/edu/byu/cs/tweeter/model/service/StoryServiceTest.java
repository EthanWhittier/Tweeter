package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceTest {


    private StoryRequest request;
    private StoryResponse response;
    private ServerFacade mockServerFacade;
    private StoryService storyServiceSpy;

    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status("status", null, null, null, currentUser));

        request = new StoryRequest(currentUser, 10, null);
        response = new StoryResponse(statuses, false);

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(request)).thenReturn(response);

        storyServiceSpy = Mockito.spy(new StoryService());
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    @Test
    void testGetStory_returnsServerFacadeResponse() {
        Assertions.assertEquals(response, storyServiceSpy.getUserStory(request));
    }


}
