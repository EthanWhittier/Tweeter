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


    Status status1 = new Status(" @Ethan Hello @TestUser", null, "10/4/2393", null, user);
    Status status2 = new Status("@Ethan Test", null, "39/23/3930", null, user);
    Status status3 = new Status("@Ethan Test2", null, "58/29/3940", null, user);
    Status status4 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status5 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status6 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status7 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status8 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status9 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status10 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status11 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status12 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status13 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status14 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status15 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status16 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status17 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status18 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status19 = new Status("@Ethan My name is.", null, "30/5/20", null, user);
    Status status20 = new Status("@Ethan My name is.", null, "30/5/20", null, user);




    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        return new PostStatusResponse(true);
    }

    public FeedResponse getFeed(FeedRequest feedRequest) {
        StoryGenerator storyGenerator = new StoryGenerator();
        mentions.add("Ethan");
        status1.setMentions(mentions);
        status2.setMentions(mentions);
        status3.setMentions(mentions);
        status4.setMentions(mentions);
        status5.setMentions(mentions);
        status6.setMentions(mentions);
        status7.setMentions(mentions);
        status8.setMentions(mentions);
        status9.setMentions(mentions);
        status10.setMentions(mentions);
        status11.setMentions(mentions);
        status12.setMentions(mentions);
        status13.setMentions(mentions);
        status14.setMentions(mentions);
        status15.setMentions(mentions);
        status16.setMentions(mentions);
        status17.setMentions(mentions);
        status18.setMentions(mentions);
        status19.setMentions(mentions);
        status20.setMentions(mentions);

        List<Status> statuses = new ArrayList<>();
        statuses.add(status1);
        statuses.add(status2);
        statuses.add(status3);
        statuses.add(status4);
        statuses.add(status5);
        statuses.add(status6);
        statuses.add(status7);
        statuses.add(status8);
        statuses.add(status9);
        statuses.add(status10);
        statuses.add(status11);
        statuses.add(status12);
        statuses.add(status13);
        statuses.add(status14);
        statuses.add(status15);
        statuses.add(status16);
        statuses.add(status17);
        statuses.add(status18);
        statuses.add(status19);
        statuses.add(status20);
        return new FeedResponse(statuses, false);
    }





}
