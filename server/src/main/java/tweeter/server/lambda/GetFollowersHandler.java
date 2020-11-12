package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;
import tweeter.model.service.response.FollowingResponse;
import tweeter.server.service.FollowersService;


public class GetFollowersHandler implements RequestHandler<FollowersRequest, FollowersResponse> {


    @Override
    public FollowersResponse handleRequest(FollowersRequest request, Context context) {
        return getFollowersService().getFollowers(request);
    }

   public FollowersService getFollowersService() {
        return new FollowersService();
   }


}
