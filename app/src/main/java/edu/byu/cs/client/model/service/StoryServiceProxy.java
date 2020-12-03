package edu.byu.cs.client.model.service;

import java.io.IOException;

import edu.byu.cs.client.DataCache;
import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.StoryServiceInterface;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;

public class StoryServiceProxy implements StoryServiceInterface {


    private final String STORY_URL = "/getstory";

    public StoryResponse getStory(StoryRequest storyRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        StoryResponse response = serverFacade.getStory(storyRequest, STORY_URL);
        if(!String.valueOf(response.getLastStatus()).isEmpty()) {
            DataCache.getInstance().setLastCreatedStory(response.getLastStatus());
        }
        return serverFacade.getStory(storyRequest, STORY_URL);
    }

    ServerFacade getServerFacade()  {
            return new ServerFacade();
    }


}
