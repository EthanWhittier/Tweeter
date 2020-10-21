package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusServiceTest {

    private PostStatusRequest request;
    private PostStatusResponse response;
    private ServerFacade mockServerFacade;
    private PostStatusService postStatusServiceSpy;


    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", null);

        request = new PostStatusRequest("status", currentUser);
        response = new PostStatusResponse(true, new Status("status", null, LocalDateTime.now(), null, currentUser));

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.postStatus(request)).thenReturn(response);

        postStatusServiceSpy = Mockito.spy(new PostStatusService());
        Mockito.when(postStatusServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    @Test
    void testPostStatus_returnServiceFacadeResponse() {
        Assertions.assertEquals(response, postStatusServiceSpy.postStatus(request));
    }



}
