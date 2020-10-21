package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenterTest {


    private StoryRequest request;
    private StoryResponse response;
    private StoryService mockStoryService;
    private StoryPresenter presenter;


    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status("status", null, null, null, currentUser));

        request = new StoryRequest(currentUser, 10, null);
        response = new StoryResponse(statuses, false);

        mockStoryService = Mockito.mock(StoryService.class);
        Mockito.when(mockStoryService.getUserStory(request)).thenReturn(response);

        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);

    }

    @Test
    void testGetStory_returnsServiceResult() {
        Assertions.assertEquals(response, presenter.getUserStory(request));
    }


}
