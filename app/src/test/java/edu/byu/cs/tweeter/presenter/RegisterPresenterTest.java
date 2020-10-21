package edu.byu.cs.tweeter.presenter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterPresenterTest {


    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterService mockRegisterService;
    private RegisterPresenter presenter;

    @BeforeEach
    void setup() throws IOException {

        User currentUser = new User("FirstName", "LastName", null);

        request = new RegisterRequest("name", "last", "username", "password");
        response = new RegisterResponse(currentUser, new AuthToken());

        mockRegisterService = Mockito.mock(RegisterService.class);
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    void testRegister_returnsRegisterService() throws IOException {
        Assertions.assertEquals(response, presenter.registerAndLogin(request));
    }


}
