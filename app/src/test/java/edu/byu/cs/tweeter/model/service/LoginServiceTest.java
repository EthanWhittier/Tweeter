package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceTest {


    private LoginRequest request;
    private LoginResponse response;
    private ServerFacade mockServerFacade;
    private LoginService loginServiceSpy;

    @BeforeEach
    void setup() throws IOException {

        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new LoginRequest("Test", "password");
        response = new LoginResponse(currentUser, new AuthToken());

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(request)).thenReturn(response);

        loginServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    void testLogin_returnServerFacadeResponse() throws IOException {
        Assertions.assertEquals(response, loginServiceSpy.login(request));
    }

}
