package edu.byu.cs.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.LoginServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;


public class LoginPresenterTest {


    private LoginRequest request;
    private LoginResponse response;
    private LoginServiceProxy mockLoginService;
    private LoginPresenter presenter;


    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null, null);

        request = new LoginRequest("Test", "password");
        response = new LoginResponse(currentUser, new AuthToken());

        mockLoginService = Mockito.mock(LoginServiceProxy.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    void testLogin_returnServiceResult() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response, presenter.login(request));
    }





}
