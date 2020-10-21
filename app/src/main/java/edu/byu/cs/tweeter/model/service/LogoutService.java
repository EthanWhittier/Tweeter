package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutService {



    public LogoutResponse logout(LogoutRequest logoutRequest) {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.logout(logoutRequest);
    }



    ServerFacade getServerFacade() {return new ServerFacade();}


}
