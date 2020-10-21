package edu.byu.cs.tweeter.model.net;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Feed;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private static Map<User, List<User>> followeesByFollower;
    private static Map<User, List<User>> followersByFollowee;


    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());
    }

    /**
     * Perform a register and if successful, returns the logged in user and an auth token. The
     * current implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request
     *
     * @param request
     * @return
     */
    public RegisterResponse register(RegisterRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new RegisterResponse(user, new AuthToken());
    }

    /**
     * Performs a logout and if successful will delete the current authtoken and clear the caches
     * current implementation is hard-coded to return a success
     * @param request
     * @return
     */
    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse(true);
    }


    /**
     * Posts a edu.byu.cs.tweeter.view.main.status and if successful will put it in the users story and all of the people
     * who follow this users edu.byu.cs.tweeter.view.main.feed
     * current implementation is hard-coded to return a dummy edu.byu.cs.tweeter.view.main.status
     *
     * @param postStatusRequest
     * @return
     */
    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        return new PostStatusResponse(true, new Status("This is a dummy edu.byu.cs.tweeter.view.main.status", null, LocalDateTime.now(), null, postStatusRequest.getAuthor()));
    }


    public StoryResponse getStory(StoryRequest storyRequest) {
        Story story = getStoryGenerator().generateStory(storyRequest.getUser(), storyRequest.getLimit());
        return new StoryResponse(story.getStatuses(), true);
    }


    public FeedResponse getFeed(FeedRequest feedRequest) {
        return new FeedResponse(new Feed(feedRequest.getUser(), getStoryGenerator().generateStory(feedRequest.getUser(), feedRequest.getLimit()).getStatuses()), true);
    }

    public FollowResponse follow(FollowRequest followRequest) {
        return new FollowResponse(true, "Follow Successful");
    }

    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) {
        return new UnfollowResponse(true, "Unfollow successful");
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. The current implementation
     * returns generated data and doesn't actually make a network request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the following response.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {


        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        List<User> allFollowees = followeesByFollower.get(request.getFollower());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }


    public FollowersResponse getFollowers(FollowersRequest followersRequest) {

        if(followersByFollowee == null) {
            followersByFollowee = initializeFollowers();
        }

        List<User> allFollowers = followersByFollowee.get(followersRequest.getFolowee());
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
        return new FollowersResponse(responseFollowers, hasMorePages);
    }

    /**
     * Determines the index for the first followee in the specified 'allFollowees' list that should
     * be returned in the current request. This will be the index of the next followee after the
     * specified 'lastFollowee'.
     *
     * @param lastFollowee the last followee that was returned in the previous request or null if
     *                     there was no previous request.
     * @param allFollowees the generated list of followees from which we are returning paged results.
     * @return the index of the first followee to be returned.
     */
    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

   //probably can delete this and combine with the other
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

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    private Map<User, List<User>> initializeFollowers() {
        Map<User, List<User>> followersByFollowee = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100, 0, 50, FollowGenerator.Sort.FOLLOWEE_FOLLOWER);

        for(Follow follow: follows) {
            List<User> followers = followersByFollowee.get(follow.getFollowee());

            if(followers == null) {
                followers = new ArrayList<>();
                followersByFollowee.put(follow.getFollowee(), followers);
            }
            followers.add(follow.getFollower());
        }

        return followersByFollowee;
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

    StoryGenerator getStoryGenerator() {return StoryGenerator.getInstance();}
}
