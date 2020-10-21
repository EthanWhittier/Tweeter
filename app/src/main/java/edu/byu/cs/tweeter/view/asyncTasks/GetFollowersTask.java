package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.presenter.FollowersPresenter;

public class GetFollowersTask extends AsyncTask<FollowersRequest, Void, FollowersResponse> {


   private final FollowersPresenter presenter;
   private final Observer observer;
   private Exception exception;


   public interface Observer {
       void followersRetrieved(FollowersResponse followersResponse);
       void handleException(Exception exception);
   }


   public GetFollowersTask(FollowersPresenter presenter, Observer observer) {
       this.presenter = presenter;
       this.observer = observer;
   }

    @Override
    protected FollowersResponse doInBackground(FollowersRequest... followersRequests) {

       FollowersResponse followersResponse = null;

       try {
           followersResponse = presenter.getFollowers(followersRequests[0]);
       } catch (Exception ex) {
           exception = ex;
       }

       return followersResponse;
    }

    @Override
    protected void onPostExecute(FollowersResponse followersResponse) {
       if(exception != null) {
           observer.handleException(exception);
       } else {
           observer.followersRetrieved(followersResponse);
       }
    }
}
