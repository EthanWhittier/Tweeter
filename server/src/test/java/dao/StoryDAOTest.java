package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tweeter.model.domain.User;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.dao.StoryDAO;
import tweeter.server.factory.FactoryManager;

public class StoryDAOTest {

    StoryDAO storyDAO;
    PostStatusRequest request;
    PostStatusResponse response;
    User testUser;

    @BeforeEach
    void setup() {
        testUser = new User("Johnny", "Apple", "johnny12", null);
        storyDAO = FactoryManager.getDAOFactory().makeStoryDAO();
        request = new PostStatusRequest("Katie is the best", testUser);
        response = new PostStatusResponse(true);
    }


    @Test
    void testPostStatusToStory() {
       PostStatusResponse testResponse = storyDAO.postStatus(request, System.currentTimeMillis());
       Assertions.assertTrue(testResponse.isSuccess());
    }


    @Test
    void testGetStory() {
        StoryRequest request = new StoryRequest(testUser, 10, 0);
        storyDAO.getStory(request);
    }


}
