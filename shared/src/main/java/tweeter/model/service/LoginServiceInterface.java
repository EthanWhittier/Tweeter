package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;

public interface LoginServiceInterface {

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;

}