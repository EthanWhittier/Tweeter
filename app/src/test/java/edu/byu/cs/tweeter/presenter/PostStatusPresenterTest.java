package edu.byu.cs.tweeter.presenter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenterTest {


    private PostStatusRequest request;
    private PostStatusResponse response;
    private PostStatusService mockPostStatusService;
    private  PostStatusPresenter presenter;

    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", null);

        request = new PostStatusRequest("Status", currentUser);
        response = new PostStatusResponse("Success");

        mockPostStatusService = Mockito.mock(PostStatusService.class);
        Mockito.when(mockPostStatusService.postStatus(request)).thenReturn(response);

        presenter = Mockito.spy(new PostStatusPresenter(new PostStatusPresenter.View() {}));
        Mockito.when(presenter.getPostStatusService()).thenReturn(mockPostStatusService);

    }

    @Test
    void testPostStatus_returnPostStatusService() {
        Assertions.assertEquals(response, presenter.postStatus(request));
    }




}
