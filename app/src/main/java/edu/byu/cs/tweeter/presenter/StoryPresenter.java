package edu.byu.cs.tweeter.presenter;

import android.view.View;

import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenter {


    private final View view;

    public interface View {

    }


    public StoryPresenter(View view) {
        this.view = view;
    }

    public StoryResponse getUserStory(StoryRequest storyRequest) {
        StoryService storyService = getStoryService();
        return storyService.getUserStory(storyRequest);
    }

    public StoryService getStoryService() {
        return new StoryService();
    }



}
