package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.MainBarPresenter;

public class UnfollowTask extends AsyncTask<UnfollowRequest, Void, UnfollowResponse> {


    private final MainBarPresenter presenter;
    private final Observer observer;
    private Exception exception;


    public interface Observer {
        void unfollowSuccessful(UnfollowResponse unfollowResponse);
        void handleException();
    }

    public UnfollowTask(MainBarPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected UnfollowResponse doInBackground(UnfollowRequest... unfollowRequests) {

        UnfollowResponse unfollowResponse = null;

        try {
            unfollowResponse = presenter.unfollow(unfollowRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return unfollowResponse;
    }

    @Override
    protected void onPostExecute(UnfollowResponse unfollowResponse) {
        if(exception != null) {
            observer.handleException();
        } else {
            observer.unfollowSuccessful(unfollowResponse);
        }
    }
}
