package tweeter.server.dao;

import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.StoryResponse;

public class StoryDAO {




    public StoryResponse getStory(StoryRequest request) {
        User currentUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", null);
        Status status1 = new Status("statues", null, "11/4/2333", null, currentUser);
        Status status2 = new Status("statusTEst", null, "99/23/3330", null, currentUser);
        Status status3 = new Status("StatusTes3", null, "52/29/3040", null, currentUser);
        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);
        return new StoryResponse(statuses, true);
    }


}
