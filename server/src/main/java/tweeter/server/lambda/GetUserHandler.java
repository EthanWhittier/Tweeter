package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.GetUserRequest;
import tweeter.model.service.response.GetUserResponse;
import tweeter.server.service.FollowService;

public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {


    @Override
    public GetUserResponse handleRequest(GetUserRequest request, Context context) {
        return getFollowService().getUser(request);
    }

    public FollowService getFollowService() {
        return new FollowService();
    }

}
