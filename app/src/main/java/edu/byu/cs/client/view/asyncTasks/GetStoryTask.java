package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;
import edu.byu.cs.client.presenter.StoryPresenter;

public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> {

    private final StoryPresenter storyPresenter;
    private final Observer observer;
    private Exception exception;


    public interface Observer {
        void storyRetrieved(StoryResponse storyResponse);
        void handleException(Exception ex);
    }


    public GetStoryTask(StoryPresenter storyPresenter, Observer observer) {
        this.storyPresenter = storyPresenter;
        this.observer = observer;

    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests) {

        StoryResponse storyResponse = null;

        try {
           storyResponse =  storyPresenter.getUserStory(storyRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return storyResponse;
    }


    @Override
    protected void onPostExecute(StoryResponse storyResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.storyRetrieved(storyResponse);
        }
    }
}
