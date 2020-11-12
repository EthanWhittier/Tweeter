package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.response.FollowResponse;
import edu.byu.cs.client.presenter.MainBarPresenter;

public class FollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {


    private final MainBarPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer{
        void followSuccessful(FollowResponse followResponse);
        void handleException();
    }

    public FollowTask(MainBarPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {
        FollowResponse followResponse = null;

        try {
            followResponse = presenter.follow(followRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return followResponse;
    }


    @Override
    protected void onPostExecute(FollowResponse followResponse) {
        if(exception != null) {
            observer.handleException();
        } else {
            observer.followSuccessful(followResponse);
        }
    }
}
