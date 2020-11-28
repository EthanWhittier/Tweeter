package edu.byu.cs.client.model.net;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tweeter.model.domain.Status;
import tweeter.model.domain.Story;
import tweeter.model.domain.User;

public class StoryGenerator {

    private static StoryGenerator instance;

    private StoryGenerator() {}

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


    }


}
