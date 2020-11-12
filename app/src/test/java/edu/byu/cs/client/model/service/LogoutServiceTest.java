package edu.byu.cs.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;

public class LogoutServiceTest {

    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutServiceProxy logoutService;

    @BeforeEach
    void setup(){

        User currentUser = new User("FirstName", "LastName", null);

        request = new LogoutRequest(currentUser, new AuthToken());
        response = new LogoutResponse(true);

        logoutService = new LogoutServiceProxy();

    }

    @Test
    void testLogout_returnServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response.isSuccess(), logoutService.logout(request).isSuccess());
    }


}
