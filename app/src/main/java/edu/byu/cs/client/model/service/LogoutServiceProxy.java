package edu.byu.cs.client.model.service;

        import java.io.IOException;

        import edu.byu.cs.client.model.net.ServerFacade;
        import tweeter.model.net.TweeterRemoteException;
        import tweeter.model.service.LoginServiceInterface;
        import tweeter.model.service.LogoutServiceInterface;
        import tweeter.model.service.request.LogoutRequest;
        import tweeter.model.service.response.LogoutResponse;

public class LogoutServiceProxy implements LogoutServiceInterface {

    private static final String URL_PATH = "/logout";


    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.logout(logoutRequest, URL_PATH);
    }


    ServerFacade getServerFacade() {return new ServerFacade();}

}
