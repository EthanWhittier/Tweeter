package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.UnfollowResponse;
import tweeter.server.service.FollowService;

public class UnfollowHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {

    @Override
    public UnfollowResponse handleRequest(UnfollowRequest request, Context context) {
        return getFollowService().unfollow(request);
    }

    public FollowService getFollowService() {
        return new FollowService();
    }
}
