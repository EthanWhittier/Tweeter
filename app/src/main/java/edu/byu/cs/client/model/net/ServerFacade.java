package edu.byu.cs.client.model.net;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.Feed;
import tweeter.model.domain.Follow;
import tweeter.model.domain.Status;
import tweeter.model.domain.Story;
import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.FollowersResponse;
import tweeter.model.service.response.FollowingResponse;
import tweeter.model.service.response.LoginResponse;
import tweeter.model.service.response.LogoutResponse;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.model.service.response.RegisterResponse;
import tweeter.model.service.response.StoryResponse;
import tweeter.model.service.response.UnfollowResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    private static Map<User, List<User>> followeesByFollower;
    private static Map<User, List<User>> followersByFollowee;

   private static final String SERVER_URL = "https://o6z8evysxk.execute-api.us-west-2.amazonaws.com/prod";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    //TODO Handle Failed Responses

    /**
     * Performs a login and if successful, returns the logged in user and an auth token. The current
     * implementation is hard-coded to return a dummy user and doesn't actually make a network
     * request.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response;

        response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        return response;
    }

    /**
     * Perform a register and if successful, returns the logged in user and an auth token.
     *
     * @param request
     * @return
     */
    public RegisterResponse register(RegisterRequest request, String urlPath) throws IOException, TweeterRemoteException {
        RegisterResponse response;

        response = clientCommunicator.doPost(urlPath, request, null, RegisterResponse.class);

        return response;

    }

    /**
     * Performs a logout and if successful will delete the current authtoken and clear the caches
     *
     * @param request
     * @return
     */
    public LogoutResponse logout(LogoutRequest request, String urlPath) throws IOException, TweeterRemoteException {

        LogoutResponse response = clientCommunicator.doPost(urlPath, request, null, LogoutResponse.class);

        return response;

    }


    /**
     * Posts a edu.byu.cs.tweeter.view.main.status and if successful will put it in the users story and all of the people
     * who follow this users edu.byu.cs.tweeter.view.main.feed
     * current implementation is hard-coded to return a dummy edu.byu.cs.tweeter.view.main.status
     *
     * @param postStatusRequest
     * @return
     */
    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, postStatusRequest, null, PostStatusResponse.class);
    }


    public StoryResponse getStory(StoryRequest storyRequest, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, storyRequest, null, StoryResponse.class);
    }


    public FeedResponse getFeed(FeedRequest feedRequest, String urlPath) throws IOException, TweeterRemoteException {
       return clientCommunicator.doPost(urlPath, feedRequest, null, FeedResponse.class);
    }

    public FollowResponse follow(FollowRequest followRequest, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, followRequest, null, FollowResponse.class);
    }

    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, unfollowRequest, null, UnfollowResponse.class);
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
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath) throws IOException, TweeterRemoteException {

        FollowingResponse response;

        response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);

        return response;

    }


    public FollowersResponse getFollowers(FollowersRequest followersRequest, String urlPath) throws IOException, TweeterRemoteException {
        return clientCommunicator.doPost(urlPath, followersRequest, null, FollowersResponse.class);
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
