package edu.byu.cs.client.model.service;

import java.io.IOException;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.FeedServiceInterface;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

public class FeedServiceProxy implements FeedServiceInterface {

    private final static String FEED_URL = "/getfeed";

    public FeedResponse getFeed(FeedRequest feedRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        FeedResponse feedResponse = serverFacade.getFeed(feedRequest, FEED_URL);
        if(feedResponse.isSuccess()) {
            loadImages(feedResponse);
        }
        return feedResponse;
    }


    private void loadImages(FeedResponse feedResponse)  {
        for(Status status : feedResponse.getStatuses()) {
            try {
                User user = status.getAuthor();
                byte[] bytes = ByteArrayUtils.bytesFromUrl(user.getAlias());
                user.setImageBytes(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerFacade getServerFacade() {return new ServerFacade();}

}
