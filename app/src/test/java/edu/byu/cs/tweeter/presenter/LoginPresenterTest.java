package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;


public class LoginPresenterTest {


    private LoginRequest request;
    private LoginResponse response;
    private LoginService mockLoginService;
    private LoginPresenter presenter;


    @BeforeEach
    void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        request = new LoginRequest("Test", "password");
        response = new LoginResponse(currentUser, new AuthToken());

        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    void testLogin_returnServiceResult() throws IOException {
        Assertions.assertEquals(response, presenter.login(request));
    }





}
