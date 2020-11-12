package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;

public interface RegisterServiceInterface {


    RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException;


}
