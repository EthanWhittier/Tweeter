package tweeter.server.dao;


import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.utils.StoryGenerator;

public class StatusDAO {


    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    User user = new User("Test", "User",
            "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");


    String mention = "Ethan";
    List<String> mentions = new ArrayList<>();




    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        return new PostStatusResponse(true);
    }







}
