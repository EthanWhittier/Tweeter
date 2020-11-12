package edu.byu.cs.client.presenter;

import java.io.IOException;

import edu.byu.cs.client.model.service.LoginServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;

/**
 * The presenter for the login functionality of the application.
 */
public class LoginPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LoginPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a login request.
     *
     * @param loginRequest the request.
     */
    public LoginResponse login(LoginRequest loginRequest) throws IOException, TweeterRemoteException {
        LoginServiceProxy loginService = getLoginService();
        return loginService.login(loginRequest);
    }

    public LoginServiceProxy getLoginService() {
        return new LoginServiceProxy();
    }

}
