package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.StoryServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;

public class StoryPresenterTest {


    private StoryRequest request;
    private StoryResponse response;
    private StoryServiceProxy mockStoryService;
    private StoryPresenter presenter;


    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", null);
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status("status", null, null, null, currentUser));

        request = new StoryRequest(currentUser, 10, null);
        response = new StoryResponse(statuses, false);

        mockStoryService = Mockito.mock(StoryServiceProxy.class);
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);

    }

    @Test
    void testGetStory_returnsServiceResult() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response, presenter.getUserStory(request));
    }


}
