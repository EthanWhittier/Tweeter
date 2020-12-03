package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.service.PostStatusService;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, PostStatusResponse> {


    @Override
    public PostStatusResponse handleRequest(PostStatusRequest request, Context context) {
        PostStatusService postStatusService = getPostStatusService();
        try {
            return postStatusService.postStatus(request);
        } catch (TweeterRemoteException e) {
            return new PostStatusResponse(e.getMessage());
        }

    }


    public PostStatusService getPostStatusService() {
        return new PostStatusService();
    }
}
