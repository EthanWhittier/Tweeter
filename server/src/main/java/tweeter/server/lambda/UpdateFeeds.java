package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import tweeter.server.dao.JsonSerializer;
import tweeter.server.dto.UpdateFeedDTO;
import tweeter.server.service.FeedService;

public class UpdateFeeds implements RequestHandler<SQSEvent, Void> {


    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        FeedService feedService = getFeedService();
        for(SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            UpdateFeedDTO updateFeedDTO = JsonSerializer.deserialize(msg.getBody(), UpdateFeedDTO.class);
            feedService.updateFeed(updateFeedDTO);
        }
        context.getLogger().log("Message sent to Update Feed SQS!");
        return null;
    }


    private FeedService getFeedService() {
        return new FeedService();
    }
}
