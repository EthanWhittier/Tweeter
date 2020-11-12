package tweeter.server.service;

import tweeter.model.service.FeedServiceInterface;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.server.dao.StatusDAO;

public class FeedService implements FeedServiceInterface {


    @Override
    public FeedResponse getFeed(FeedRequest feedRequest) {
        return getStatusDAO().getFeed(feedRequest);
    }


    public StatusDAO getStatusDAO() {
        return new StatusDAO();
    }

}
