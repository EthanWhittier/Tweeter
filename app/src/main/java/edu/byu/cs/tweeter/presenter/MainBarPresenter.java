package edu.byu.cs.tweeter.presenter;


import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class MainBarPresenter {


    private View view;

    public interface View {

    }

    public MainBarPresenter(View view) {this.view = view;}


    public LogoutResponse logout(LogoutRequest logoutRequest) {
        LogoutService logoutService = getLogoutService();
        return logoutService.logout(logoutRequest);
    }

    public FollowResponse follow(FollowRequest followRequest) {
        FollowService followService = getFollowService();
        return followService.follow(followRequest);
    }

    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) {
        FollowService followService = getFollowService();
        return followService.unfollow(unfollowRequest);
    }


    public LogoutService getLogoutService() {
        return new LogoutService();
    }

    public FollowService getFollowService() {
        return new FollowService();
    }


}
