package tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tweeter.model.domain.User;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.response.FollowersResponse;

public class FollowerDAO {

    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";


    private final User user1 = new User("Allen", "Anderson", MALE_IMAGE_URL);
    private final User user2 = new User("Amy", "Ames", FEMALE_IMAGE_URL);
    private final User user3 = new User("Bob", "Bobson", MALE_IMAGE_URL);
    private final User user4 = new User("Bonnie", "Beatty", FEMALE_IMAGE_URL);
    private final User user5 = new User("Chris", "Colston", MALE_IMAGE_URL);
    private final User user6 = new User("Cindy", "Coats", FEMALE_IMAGE_URL);
    private final User user7 = new User("Dan", "Donaldson", MALE_IMAGE_URL);
    private final User user8 = new User("Dee", "Dempsey", FEMALE_IMAGE_URL);
    private final User user9 = new User("Elliott", "Enderson", MALE_IMAGE_URL);
    private final User user10 = new User("Elizabeth", "Engle", FEMALE_IMAGE_URL);
    private final User user11 = new User("Frank", "Frandson", MALE_IMAGE_URL);
    private final User user12 = new User("Fran", "Franklin", FEMALE_IMAGE_URL);
    private final User user13 = new User("Gary", "Gilbert", MALE_IMAGE_URL);
    private final User user14 = new User("Giovanna", "Giles", FEMALE_IMAGE_URL);
    private final User user15 = new User("Henry", "Henderson", MALE_IMAGE_URL);
    private final User user16 = new User("Helen", "Hopwell", FEMALE_IMAGE_URL);
    private final User user17 = new User("Igor", "Isaacson", MALE_IMAGE_URL);
    private final User user18 = new User("Isabel", "Isaacson", FEMALE_IMAGE_URL);
    private final User user19 = new User("Justin", "Jones", MALE_IMAGE_URL);
    private final User user20 = new User("Jill", "Johnson", FEMALE_IMAGE_URL);



    public FollowersResponse getFollowers(FollowersRequest followersRequest) {


        List<User> allFollowers = getDummyFollowers();
        List<User> responseFollowers = new ArrayList<>(followersRequest.getLimit());

        boolean hasMorePages = false;

        if(followersRequest.getLimit() > 0) {
            if(allFollowers != null) {
                int followersIndex = getFollowersStartingIndex(followersRequest.getLastFollower(), allFollowers);

                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < followersRequest.getLimit(); followersIndex++, limitCounter++) {
                    responseFollowers.add(allFollowers.get(followersIndex));
                }
                hasMorePages = followersIndex < allFollowers.size();
            }
        }
        return new FollowersResponse(getDummyFollowers(), hasMorePages);
    }

    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {

        int followersIndex = 0;

        if(lastFollower != null) {

            for(int i = 0; i < allFollowers.size(); i++) {
                if(lastFollower.equals(allFollowers.get(i))) {
                    followersIndex = i + 1;
                }
            }
        }
        return followersIndex;
    }

    List<User> getDummyFollowers() {
        return Arrays.asList(user1, user2, user3);
    }


}



