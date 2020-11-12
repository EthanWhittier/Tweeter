package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;

public interface StoryServiceInterface {


    StoryResponse getStory(StoryRequest request) throws IOException, TweeterRemoteException;

}
