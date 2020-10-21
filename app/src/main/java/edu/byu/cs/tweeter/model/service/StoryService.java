package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryService {



    public StoryResponse getUserStory(StoryRequest storyRequest) {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.getStory(storyRequest);
    }



    ServerFacade getServerFacade()  {
            return new ServerFacade();
    }


}
