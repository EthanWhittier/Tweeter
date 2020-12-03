package edu.byu.cs.client.model.service;

        import java.io.IOException;

        import edu.byu.cs.client.DataCache;
        import edu.byu.cs.client.model.net.ServerFacade;
        import tweeter.model.net.TweeterRemoteException;
        import tweeter.model.service.LoginServiceInterface;
        import tweeter.model.service.LogoutServiceInterface;
        import tweeter.model.service.request.LogoutRequest;
        import tweeter.model.service.response.LogoutResponse;

public class LogoutServiceProxy implements LogoutServiceInterface {

    private static final String URL_PATH = "/logout";

    //TODO better error handle???
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LogoutResponse response;
        try {
            response = serverFacade.logout(logoutRequest, URL_PATH);
            clearData();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearData() {
       DataCache dataCache =  DataCache.getInstance();
       dataCache.setLoggedInUser(null);
       dataCache.setAuthToken(null);
       dataCache.setLastCreatedStory(0);
       dataCache.setLastCreatedFeed(0);
    }

    ServerFacade getServerFacade() {return new ServerFacade();}

}
