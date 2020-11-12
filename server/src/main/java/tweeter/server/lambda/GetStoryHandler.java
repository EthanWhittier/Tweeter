package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;
import tweeter.server.service.StoryService;


public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {


    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        return getStoryService().getStory(request);
    }

    StoryService getStoryService() {
        return new StoryService();
    }
}
