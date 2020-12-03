package edu.byu.cs.client.presenter;

import java.io.IOException;

import edu.byu.cs.client.model.service.FollowServiceProxy;
import edu.byu.cs.client.model.service.StoryServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.GetUserRequest;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.GetUserResponse;
import tweeter.model.service.response.StoryResponse;

public class StoryPresenter {


    private final View view;

    public interface View {

    }


    public StoryPresenter(View view) {
        this.view = view;
    }

    public StoryResponse getUserStory(StoryRequest storyRequest) throws IOException, TweeterRemoteException {
        StoryServiceProxy storyService = getStoryService();
        return storyService.getStory(storyRequest);
    }

    public GetUserResponse getUser(GetUserRequest request) throws IOException, TweeterRemoteException {
        return getFollowService().getUser(request);
    }

    public StoryServiceProxy getStoryService() {
        return new StoryServiceProxy();
    }

    public FollowServiceProxy getFollowService() {return new FollowServiceProxy();}

}
