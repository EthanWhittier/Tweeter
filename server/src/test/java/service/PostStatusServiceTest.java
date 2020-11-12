package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import tweeter.model.domain.Status;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.dao.StatusDAO;
import tweeter.server.service.PostStatusService;

public class PostStatusServiceTest {


    private PostStatusRequest request;
    private PostStatusResponse response;
    private StatusDAO statusDAOMock;
    private PostStatusService postStatusServiceSpy;


    @BeforeEach
    void setup() {

        request = new PostStatusRequest("Test", null);
        response = new PostStatusResponse(true);

        statusDAOMock = Mockito.mock(StatusDAO.class);
        Mockito.when(statusDAOMock.postStatus(request)).thenReturn(response);

        postStatusServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(postStatusServiceSpy.getStatusDAO()).thenReturn(statusDAOMock);

    }

    @Test
    void testPostStatusService() {
        Assertions.assertEquals(response.isSuccess(), postStatusServiceSpy.postStatus(request).isSuccess());
    }



}
