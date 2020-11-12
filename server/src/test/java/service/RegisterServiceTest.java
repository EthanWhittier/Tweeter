package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.dao.UserDAO;
import tweeter.server.service.RegisterService;


public class RegisterServiceTest {


    private RegisterRequest request;
    private RegisterResponse response;
    private UserDAO userDAOmock;
    private RegisterService registerServiceSpy;


    @BeforeEach
    void setup() {

        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new RegisterRequest();

        response = new RegisterResponse(new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"), new AuthToken());

        userDAOmock = Mockito.mock(UserDAO.class);
        Mockito.when(userDAOmock.register(request)).thenReturn(response);


        registerServiceSpy = Mockito.spy(new RegisterService());
        Mockito.when(registerServiceSpy.getUserDAO()).thenReturn(userDAOmock);

    }

    @Test
    void testregisterService_returnsMockUserDAOData() {
        Assertions.assertEquals(registerServiceSpy.register(request), response);
    }


}
