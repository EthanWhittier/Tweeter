package edu.byu.cs.client.presenter;

import java.io.IOException;

import edu.byu.cs.client.model.service.PostStatusServiceProxy;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;

public class PostStatusPresenter {

    private final View view;

    public interface View {

    }

    public PostStatusPresenter(View view) {
        this.view = view;
    }


    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) throws IOException, TweeterRemoteException {
        PostStatusServiceProxy postStatusService = getPostStatusService();
        return postStatusService.postStatus(postStatusRequest);
    }

    public PostStatusServiceProxy getPostStatusService() {
        return new PostStatusServiceProxy();
    }

}
