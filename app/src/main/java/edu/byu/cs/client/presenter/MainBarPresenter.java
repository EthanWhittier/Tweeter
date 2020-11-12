package edu.byu.cs.client.presenter;


import java.io.IOException;

import edu.byu.cs.client.model.service.FollowServiceProxy;
import edu.byu.cs.client.model.service.LogoutServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.LogoutResponse;
import tweeter.model.service.response.UnfollowResponse;

public class MainBarPresenter {


    private View view;

    public interface View {

    }

    public MainBarPresenter(View view) {this.view = view;}


    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, TweeterRemoteException {
        LogoutServiceProxy logoutService = getLogoutService();
        return logoutService.logout(logoutRequest);
    }

    public FollowResponse follow(FollowRequest followRequest) throws IOException, TweeterRemoteException {
        FollowServiceProxy followService = getFollowService();
        return followService.follow(followRequest);
    }

    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) throws IOException, TweeterRemoteException {
        FollowServiceProxy followService = getFollowService();
        return followService.unfollow(unfollowRequest);
    }


    public LogoutServiceProxy getLogoutService() {
        return new LogoutServiceProxy();
    }

    public FollowServiceProxy getFollowService() {
        return new FollowServiceProxy();
    }


}
