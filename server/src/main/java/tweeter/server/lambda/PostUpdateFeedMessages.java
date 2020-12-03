package tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;


import java.util.ArrayList;
import java.util.List;

import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.JsonSerializer;
import tweeter.server.dao.ResultsPage;
import tweeter.server.dto.PostStatusDTO;
import tweeter.server.dto.UpdateFeedDTO;
import tweeter.server.factory.FactoryManager;
import tweeter.server.sqs.PostStatusSQS;
import tweeter.server.sqs.UpdateFeedSQS;

public class PostUpdateFeedMessages implements RequestHandler<SQSEvent, Void> {

    private static final int PAGE_SIZE = 25;

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        ResultsPage results = null;
        for(SQSEvent.SQSMessage msg : sqsEvent.getRecords()) {
            PostStatusDTO postStatusDTO = JsonSerializer.deserialize(msg.getBody(), PostStatusDTO.class);
            while(results == null || results.hasLastKey()) {
                String lastFollower = ((results != null) ? results.getLastKey() : null);
                results = getFollowDAO().getFollowersOfUserPage(postStatusDTO.getAuthor(), PAGE_SIZE, lastFollower);
                String json = JsonSerializer.serialize(new UpdateFeedDTO(postStatusDTO.getStatus(), results.getValues(), postStatusDTO.getAuthor(), postStatusDTO.getCreated()));
                UpdateFeedSQS.sendMessage(json);
            }
        }
        return null;
    }


    public FollowDAO getFollowDAO() {
        return FactoryManager.getDAOFactory().makeFollowDAO();
    }
}
