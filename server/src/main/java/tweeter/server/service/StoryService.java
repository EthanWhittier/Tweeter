package tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.service.StoryServiceInterface;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.PagedResponse;
import tweeter.model.service.response.StoryResponse;
import tweeter.server.dao.ResultsPage;
import tweeter.server.dao.StoryDAO;
import tweeter.server.factory.FactoryManager;

public class StoryService implements StoryServiceInterface {


    @Override
    public StoryResponse getStory(StoryRequest request) {
        StoryDAO storyDAO = getStoryDAO();
        StoryResponse response = storyDAO.getStory(request);

        for(Status status : response.getUserStatuses()) {
            List<String> mentions = new ArrayList<>();
            for(String s : status.getStatus().split(" ")) {
                if(s.startsWith("@")) {
                    mentions.add(s);
                }
            }
            status.setMentions(mentions);
        }
        return getStoryDAO().getStory(request);
    }


    private void getMentions(List<Status> statuses) {
        for(Status status : statuses) {
            List<String> mentions = new ArrayList<>();
            for(String s : status.getStatus().split(" ")) {
                if(s.startsWith("@")) {
                    mentions.add(s);
                }
            }
            status.setMentions(mentions);
        }
    }

    public StoryDAO getStoryDAO() {
        return FactoryManager.getDAOFactory().makeStoryDAO();
    }

}
