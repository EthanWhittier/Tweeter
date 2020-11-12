package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import edu.byu.cs.client.presenter.FeedPresenter;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {


    private final FeedPresenter presenter;
    private final Observer observer;
    private Exception exception;


    public interface Observer {
        void feedRetrieved(FeedResponse feedResponse);
        void handleException(Exception exception);
    }


    public GetFeedTask(FeedPresenter presenter, Observer observer) {
        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {

        FeedResponse feedResponse = null;
        try {
           feedResponse = presenter.getFeed(feedRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return feedResponse;
    }


    @Override
    protected void onPostExecute(FeedResponse feedResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.feedRetrieved(feedResponse);
        }
    }

}
