package tweeter.server.service;

import tweeter.model.service.StoryServiceInterface;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;
import tweeter.server.dao.StoryDAO;

public class StoryService implements StoryServiceInterface {


    @Override
    public StoryResponse getStory(StoryRequest request) {
        return getStoryDAO().getStory(request);
    }

    public StoryDAO getStoryDAO() {
        return new StoryDAO();
    }

}
