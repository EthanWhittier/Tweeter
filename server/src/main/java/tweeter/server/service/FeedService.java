package tweeter.server.service;

import tweeter.model.service.FeedServiceInterface;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.server.dao.FeedDAO;
import tweeter.server.dao.StatusDAO;
import tweeter.server.dto.UpdateFeedDTO;
import tweeter.server.factory.FactoryManager;

public class FeedService implements FeedServiceInterface {





    @Override
    public FeedResponse getFeed(FeedRequest feedRequest) {
        return getFeedDAO().getFeed(feedRequest);
    }


    public void updateFeed(UpdateFeedDTO updateFeedDTO) {
        FeedDAO feedDAO = getFeedDAO();
        for (String follower : updateFeedDTO.getFollowers()) {
            feedDAO.updateFeed(updateFeedDTO.getAuthor(), updateFeedDTO.getStatus(), follower, updateFeedDTO.getCreated());
        }
    }

    public StatusDAO getStatusDAO() {
        return new StatusDAO();
    }

    public FeedDAO getFeedDAO() {return FactoryManager.getDAOFactory().makeFeedDAO(); }



}
