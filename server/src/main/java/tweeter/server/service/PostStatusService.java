package tweeter.server.service;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.PostStatusServiceInterface;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.dao.JsonSerializer;
import tweeter.server.dao.StatusDAO;
import tweeter.server.dao.StoryDAO;
import tweeter.server.dto.PostStatusDTO;
import tweeter.server.factory.FactoryManager;
import tweeter.server.sqs.PostStatusSQS;

public class PostStatusService implements PostStatusServiceInterface {


    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) throws TweeterRemoteException {
        try {
            long created = System.currentTimeMillis();
            PostStatusResponse response = getStoryDAO().postStatus(request, created);
            sendMessageToQueue(request.getStatus(), request.getAuthor().getAlias(), created);
            return response;
        } catch(Exception e) {
            throw new TweeterRemoteException(e.getMessage(), null, null);
        }
    }


    private void sendMessageToQueue(String status, String author, long created) {
        String message = JsonSerializer.serialize(new PostStatusDTO(status, author, created));
        PostStatusSQS.sendMessage(message);
    }

    public StoryDAO getStoryDAO() {
        return FactoryManager.getDAOFactory().makeStoryDAO();
    }
}
