package edu.byu.cs.client.model.service;



import java.io.IOException;

import edu.byu.cs.client.util.ByteArrayUtils;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.RegisterServiceInterface;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;


/**
 * Contains business logic for registering a user
 */
public class RegisterServiceProxy implements RegisterServiceInterface {


    private static final String URL_PATH = "/register";

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        RegisterResponse registerResponse = serverFacade.register(request, URL_PATH);

        if(registerResponse.isSuccess()) {
            loadImage(registerResponse.getUser());
        }

        return registerResponse;
    }


    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    private void loadImage(User user) throws IOException {
        byte[] bytes = ByteArrayUtils.bytesFromUrl(user.getAlias());
        user.setImageBytes(bytes);
    }



    ServerFacade getServerFacade() {return new ServerFacade(); }





}
