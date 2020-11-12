package edu.byu.cs.client.view.asyncTasks;

import android.os.AsyncTask;

import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.client.presenter.MainBarPresenter;

public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {


    private final MainBarPresenter presenter;
    private final Observer observer;
    private Exception exception;


   public interface Observer {
       void logoutSuccessful(LogoutResponse logoutResponse);
       void logoutUnsuccessful(LogoutResponse logoutResponse);
       void handleException(Exception exception);
    }


    public LogoutTask(Observer observer, MainBarPresenter presenter) {
       if(observer == null) {
           throw new NullPointerException();
       }
       this.observer = observer;
       this.presenter = presenter;
    }


    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {
        LogoutResponse logoutResponse = null;

        try {

            logoutResponse = presenter.logout(logoutRequests[0]);

        } catch (Exception ex) {
            exception = ex;
        }

        return logoutResponse;
    }



    @Override
    protected void onPostExecute(LogoutResponse logoutResponse) {
       if(exception != null) {
           observer.handleException(exception);
       } else if(logoutResponse.isSuccess()) {
           observer.logoutSuccessful(logoutResponse);
       } else {
           observer.logoutUnsuccessful(logoutResponse);
       }
    }

}
