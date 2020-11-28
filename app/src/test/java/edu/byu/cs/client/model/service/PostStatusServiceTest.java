package edu.byu.cs.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;

public class PostStatusServiceTest {

    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusServiceProxy postStatusService;


    @BeforeEach
    void setup() {

        User currentUser = new User("Test", "User", null, null);

        request = new PostStatusRequest("status", currentUser);
        response = new PostStatusResponse(true);

        postStatusService = Mockito.spy(new PostStatusServiceProxy());

    }

    @Test
    void testPostStatusSuccessful() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response.isSuccess(), postStatusService.postStatus(request).isSuccess());
    }



}
