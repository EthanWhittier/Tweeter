package edu.byu.cs.client.presenter;

import java.io.IOException;

import edu.byu.cs.client.model.service.RegisterServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;

/**
 * The presenter for the register functionality of this application
 */
public class RegisterPresenter {


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
    public RegisterPresenter(View view) {this.view = view;}


    /**
     * Makes a Register Request
     *
     * @param registerRequest
     * @return
     */
    public RegisterResponse registerAndLogin(RegisterRequest registerRequest) throws IOException, TweeterRemoteException {
        RegisterServiceProxy registerService = getRegisterService();
        return registerService.register(registerRequest);
    }


    public RegisterServiceProxy getRegisterService() {
        return new RegisterServiceProxy();
    }


}
