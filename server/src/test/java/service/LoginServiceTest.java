package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.server.dao.UserDAO;
import tweeter.server.service.LoginService;

public class LoginServiceTest {


    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private UserDAO userDAOMock;
    private LoginService loginServiceSpy;


    @BeforeEach
    void setup() {

        loginRequest = new LoginRequest("Test", "password");
        loginResponse = new LoginResponse(new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"), new AuthToken());

        userDAOMock = Mockito.mock(UserDAO.class);
        Mockito.when(userDAOMock.login(loginRequest)).thenReturn(loginResponse);

        loginServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(loginServiceSpy.getUserDAO()).thenReturn(userDAOMock);


    }

    @Test
    void testLoginService_returnsUserDAOData() {
        Assertions.assertEquals(loginResponse, loginServiceSpy.login(loginRequest));
    }


}
