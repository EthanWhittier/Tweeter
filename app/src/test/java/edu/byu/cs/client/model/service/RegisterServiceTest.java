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
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;

public class RegisterServiceTest {


    private RegisterRequest request;
    private RegisterResponse response;

    private RegisterServiceProxy registerService;
    private final String REGISTER_URL = "/register";

    @BeforeEach
    void setup() throws IOException {
        User currentUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new RegisterRequest("first", "last", "username", "password");
        response = new RegisterResponse(currentUser, new AuthToken());

        registerService = new RegisterServiceProxy();


    }

    @Test
    void testRegister_returnServerFacadeResponse() throws IOException, TweeterRemoteException {
        Assertions.assertEquals(response.getUser(), registerService.register(request).getUser());
    }



}
