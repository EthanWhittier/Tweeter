package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.client.presenter.StoryPresenter;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.GetUserRequest;
import tweeter.model.service.response.GetUserResponse;

public class GetUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {

    private StoryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    public interface Observer {
        void userRetrieved(GetUserResponse response);
        void handleException(Exception e);
    }

    public GetUserTask(StoryPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... getUserRequests) {
        GetUserResponse response = null;
        try {
            response = presenter.getUser(getUserRequests[0]);
        } catch (Exception e) {
            exception = e;
        }
        return response;
    }

    @Override
    protected void onPostExecute(GetUserResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.userRetrieved(response);
        }
    }





}
