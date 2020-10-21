package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;


import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.view.main.MainActivity;

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
