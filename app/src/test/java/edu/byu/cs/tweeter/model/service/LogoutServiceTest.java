package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutServiceTest {

    private LogoutRequest request;
    private LogoutResponse response;
    private ServerFacade mockServerFacade;
    private  LogoutService logoutServiceSpy;

    @BeforeEach
    void setup() {

        User currentUser = new User("FirstName", "LastName", null);

        request = new LogoutRequest(currentUser, new AuthToken());
        response = new LogoutResponse(true);

        mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(request)).thenReturn(response);

        logoutServiceSpy = Mockito.spy(new LogoutService());
        Mockito.when(logoutServiceSpy.getServerFacade()).thenReturn(mockServerFacade);

    }

    @Test
    void testLogout_returnServerFacadeResponse() {
        Assertions.assertEquals(response, logoutServiceSpy.logout(request));
    }


}
