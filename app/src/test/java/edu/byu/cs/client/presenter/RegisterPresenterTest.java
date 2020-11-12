package edu.byu.cs.client.presenter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.service.RegisterServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;

public class RegisterPresenterTest {


    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterServiceProxy mockRegisterService;
    private RegisterPresenter presenter;

    @BeforeEach
    void setup() throws IOException, TweeterRemoteException {

        User currentUser = new User("FirstName", "LastName", null);

        request = new RegisterRequest("name", "last", "username", "password");
        response = new RegisterResponse(currentUser, new AuthToken());

        mockRegisterService = Mockito.mock(RegisterServiceProxy.class);
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    void testRegister_returnsRegisterService() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response, presenter.registerAndLogin(request));
    }


}
