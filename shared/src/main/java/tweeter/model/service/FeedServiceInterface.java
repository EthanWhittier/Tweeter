package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;

public interface FeedServiceInterface {

    public FeedResponse getFeed(FeedRequest feedRequest) throws IOException, TweeterRemoteException;

}
