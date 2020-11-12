package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.server.service.FeedService;


public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {


    @Override
    public FeedResponse handleRequest(FeedRequest request, Context context) {
        return getFeedService().getFeed(request);
    }

    public FeedService getFeedService() {
        return new FeedService();
    }
}
