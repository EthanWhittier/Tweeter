package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import tweeter.model.domain.Status;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.dao.StatusDAO;
import tweeter.server.dao.StoryDAO;
import tweeter.server.service.PostStatusService;

public class PostStatusServiceTest {


    private PostStatusRequest request;
    private PostStatusResponse response;
    private StoryDAO storyDAOMock;
    private PostStatusService postStatusServiceSpy;


    @BeforeEach
    void setup() {

        request = new PostStatusRequest("Test", null);
        response = new PostStatusResponse(true);

        storyDAOMock = Mockito.mock(StoryDAO.class);
        Mockito.when(storyDAOMock.postStatus(request, System.currentTimeMillis())).thenReturn(response);

        postStatusServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(postStatusServiceSpy.getStoryDAO()).thenReturn(storyDAOMock);

    }

    @Test
    void testPostStatusService() throws TweeterRemoteException {
        Assertions.assertEquals(response.isSuccess(), postStatusServiceSpy.postStatus(request).isSuccess());
    }



}
