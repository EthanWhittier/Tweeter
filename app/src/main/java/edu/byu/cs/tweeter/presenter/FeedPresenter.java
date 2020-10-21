package edu.byu.cs.tweeter.presenter;

import android.view.View;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenter {


    private View view;

    public interface View {}


    public FeedPresenter(View view) {this.view = view;}

    public FeedResponse getFeed(FeedRequest feedRequest) throws IOException {
        FeedService feedService = getFeedService();
        return feedService.getFeed(feedRequest);
    }

    public FeedService getFeedService() {
        return new FeedService();
    }




}
