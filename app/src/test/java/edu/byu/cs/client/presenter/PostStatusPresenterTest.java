package edu.byu.cs.client.presenter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.PostStatusServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenterTest {


    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusServiceProxy mockPostStatusService;
    private  PostStatusPresenter presenter;

    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {

        User currentUser = new User("FirstName", "LastName", null, null);

        request = new PostStatusRequest("Status", currentUser);
        response = new PostStatusResponse("Success");

        mockPostStatusService = Mockito.mock(PostStatusServiceProxy.class);
        Mockito.when(mockPostStatusService.postStatus(request)).thenReturn(response);

        presenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusService);

    }

    @Test
    void testPostStatus_returnPostStatusService() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response, presenter.postStatus(request));
    }




}
