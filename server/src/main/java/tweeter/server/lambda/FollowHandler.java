package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.server.service.FollowService;

public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {


    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        return getFollowService().follow(request);
    }


    public FollowService getFollowService() {
        return new FollowService();
    }
}
