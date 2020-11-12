package tweeter.server.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.Story;
import tweeter.model.domain.User;

public class StoryGenerator {

    private static StoryGenerator instance;

    public StoryGenerator() {}

    public static StoryGenerator getInstance() {
        if(instance == null) {
            instance = new StoryGenerator();
        }

        return instance;
    }


    public Story generateStory(User user, int statusCount) {
        List<Status> statuses = new ArrayList<>(statusCount);
        generateStatuses((ArrayList<Status>) statuses, statusCount);

        return new Story(user, statuses);

    }

    private void generateStatuses(ArrayList<Status> statuses, int statusCount) {

        List<String> mentions = new ArrayList<>();
        mentions.add("DummyUser");
        List<String> urls = new ArrayList<>();
        urls.add("https://google.com");

        while(statuses.size() < statusCount) {
           statuses.add(new Status("Post", null, LocalDateTime.now().toString(), null, new User("Test", "User",
                   "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"))); //Todo add in values here later for testing
           statuses.add(new Status("Post mention: @DummyUser url: https://google.com", urls, LocalDateTime.now().toString(), mentions, new User("Test", "User",
                   "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png")));
           statuses.add(new Status("Post url: https://google.com", urls, LocalDateTime.now().toString(), null, new User("Test", "User",
                   "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png")));
        }
    }


}
