package edu.byu.cs.client.presenter;

import java.io.IOException;

import edu.byu.cs.client.model.service.FeedServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;

public class FeedPresenter {


    private View view;

    public interface View {}


    public FeedPresenter(View view) {this.view = view;}

    public FeedResponse getFeed(FeedRequest feedRequest) throws IOException, TweeterRemoteException {
        FeedServiceProxy feedService = getFeedService();
        return feedService.getFeed(feedRequest);
    }

    public FeedServiceProxy getFeedService() {
        return new FeedServiceProxy();
    }




}
