package tweeter.server.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.LogoutServiceInterface;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;
import tweeter.server.dao.UserDAO;

public class LogoutService implements LogoutServiceInterface {


    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest)  {
        return getUserDAO().logout(logoutRequest);
    }


    //TODO: UserDAO or other??
    public UserDAO getUserDAO() {return new UserDAO();}
}
