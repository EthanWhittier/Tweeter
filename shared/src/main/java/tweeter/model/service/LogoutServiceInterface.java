package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;

public interface LogoutServiceInterface {




    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, TweeterRemoteException;

}
