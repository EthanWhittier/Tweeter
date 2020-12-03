package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;
import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.UserDAO;
import tweeter.server.service.LogoutService;

public class LogoutServiceTest {


    private LogoutRequest request;
    private LogoutResponse response;
    private AuthTokenDAO AuthTokenDAOMock;
    private LogoutService logoutServiceSpy;


    @BeforeEach
    void setup() {

        //Dummy Request
        request = new LogoutRequest(new User(null, null, null, null), new AuthToken());
        response = new LogoutResponse(true);

        AuthTokenDAOMock = Mockito.mock(AuthTokenDAO.class);
        Mockito.when(AuthTokenDAOMock.logout(request.getAuthToken().getAuthToken())).thenReturn(true);

        logoutServiceSpy = Mockito.spy(new LogoutService());
        Mockito.when(logoutServiceSpy.getAuthTokenDAO()).thenReturn(AuthTokenDAOMock);


    }

    @Test
    void testLogoutSuccess() {
        Assertions.assertEquals(response.isSuccess(), logoutServiceSpy.logout(request).isSuccess());
    }


}
