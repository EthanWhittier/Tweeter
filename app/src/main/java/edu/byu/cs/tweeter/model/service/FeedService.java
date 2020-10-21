package edu.byu.cs.tweeter.model.service;

import android.view.View;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class FeedService {


    public FeedResponse getFeed(FeedRequest feedRequest) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        FeedResponse feedResponse = serverFacade.getFeed(feedRequest);
        if(feedResponse.isSuccess()) {
            loadImages(feedResponse);
        }
        return feedResponse;
    }

    private void loadImages(FeedResponse feedResponse) throws IOException {
        for(Status status : feedResponse.getFeed().getStatuses()) {
            User user = status.getAuthor();
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }


    public ServerFacade getServerFacade() {return new ServerFacade();}

}
