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
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;

public class LoginServiceTest {


    private LoginRequest request;
    private LoginResponse response;
    private LoginServiceProxy loginService;
    private final String LOGIN_URL = "/login";

    @BeforeEach
    void setup() {


        User currentUser = new User("Test", "User", "@TestUser", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new LoginRequest("Test", "password");
        response = new LoginResponse(currentUser, new AuthToken());

        loginService = new LoginServiceProxy();

    }

    @Test
    void testLogin_returnServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response.getUser(), loginService.login(request).getUser());
    }

    @Test
    void testLoginFail()  {
       Assertions.assertThrows(TweeterRemoteException.class, ()-> {loginService.login(null);});
    }

}
