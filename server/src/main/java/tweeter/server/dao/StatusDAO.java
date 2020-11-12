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

    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        return new PostStatusResponse(true);
    }

    public FeedResponse getFeed(FeedRequest feedRequest) {
        StoryGenerator storyGenerator = new StoryGenerator();
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        Status status1 = new Status("Hello", null, "10/4/2393", null, user);
        Status status2 = new Status("Test", null, "39/23/3930", null, user);
        Status status3 = new Status("Test2", null, "58/29/3940", null, user);
        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);
        return new FeedResponse(statuses, true);
    }


}
