package edu.byu.cs.tweeter.presenter;

import android.view.View;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenter {

    private final View view;

    public interface View {

    }

    public PostStatusPresenter(View view) {
        this.view = view;
    }


    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        PostStatusService postStatusService = getPostStatusService();
        return postStatusService.postStatus(postStatusRequest);
    }

    public PostStatusService getPostStatusService() {
        return new PostStatusService();
    }

}
