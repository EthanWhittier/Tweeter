package tweeter.server.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.LogoutServiceInterface;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;
import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.UserDAO;
import tweeter.server.factory.FactoryManager;

public class LogoutService implements LogoutServiceInterface {


    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest)  {
        if(getAuthTokenDAO().logout(logoutRequest.getAuthToken().getAuthToken())) {
            return new LogoutResponse(true);
        } else {
            return new LogoutResponse(false);
        }
    }




    public AuthTokenDAO getAuthTokenDAO() {
       return FactoryManager.getDAOFactory().makeAuthTokenDAO();
    }
}
