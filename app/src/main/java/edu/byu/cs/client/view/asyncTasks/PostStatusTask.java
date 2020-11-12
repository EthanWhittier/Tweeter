package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;


import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.client.presenter.PostStatusPresenter;

public class PostStatusTask extends AsyncTask<PostStatusRequest, Void, PostStatusResponse> {

    private final PostStatusPresenter presenter;
    private final Observer observer;
    private Exception exception;


    public interface Observer {
        void postStatusSuccessful(PostStatusResponse postStatusResponse);
        void postStatusUnsuccessful(PostStatusResponse postStatusResponse);
        void handleStatusException(Exception ex);
    }

    public PostStatusTask(PostStatusPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected PostStatusResponse doInBackground(PostStatusRequest... postStatusRequests) {
        PostStatusResponse postStatusResponse = null;

        try {
            postStatusResponse = presenter.postStatus(postStatusRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return postStatusResponse;

    }


    @Override
    protected void onPostExecute(PostStatusResponse postStatusResponse) {
        if(exception != null) {
            observer.handleStatusException(exception);
        } else if(postStatusResponse.isSuccess()) {
            observer.postStatusSuccessful(postStatusResponse);
        } else {
            observer.postStatusUnsuccessful(postStatusResponse);
        }
    }


}
