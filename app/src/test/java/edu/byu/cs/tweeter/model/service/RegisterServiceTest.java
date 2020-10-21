package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceTest {


    private RegisterRequest request;
    private RegisterResponse response;
    private ServerFacade mockServerFacade;
    private RegisterService registerServiceSpy;

    @BeforeEach
    void setup() {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new RegisterRequest("first", "last", "username", "password");
        response = new RegisterResponse(currentUser, new AuthToken());

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(request)).thenReturn(response);

        registerServiceSpy = Mockito.spy(new RegisterService());
        Mockito.when(registerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    @Test
    void testRegister_returnServerFacadeResponse() throws IOException {
        Assertions.assertEquals(response, registerServiceSpy.register(request));
    }



}
