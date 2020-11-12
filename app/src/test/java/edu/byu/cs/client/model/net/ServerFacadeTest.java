package edu.byu.cs.client.model.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.Follow;
import tweeter.model.domain.Status;
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

class ServerFacadeTest {

    private final User user1 = new User("Daffy", "Duck", "");
    private final User user2 = new User("Fred", "Flintstone", "");
    private final User user3 = new User("Barney", "Rubble", ""); // 2 followees
    private final User user4 = new User("Wilma", "Rubble", "");
    private final User user5 = new User("Clint", "Eastwood", ""); // 6 followees
    private final User user6 = new User("Mother", "Teresa", ""); // 7 followees
    private final User user7 = new User("Harriett", "Hansen", "");
    private final User user8 = new User("Zoe", "Zabriski", "");
    private final User user9 = new User("Albert", "Awesome", ""); // 1  followee
    private final User user10 = new User("Star", "Student", "");
    private final User user11 = new User("Bo", "Bungle", "");
    private final User user12 = new User("Susie", "Sampson", "");

    private final Follow follow1 = new Follow(user9, user5);

    private final Follow follow2 = new Follow(user3, user1);
    private final Follow follow3 = new Follow(user3, user8);

    private final Follow follow4 = new Follow(user5,  user9);
    private final Follow follow5 = new Follow(user5,  user11);
    private final Follow follow6 = new Follow(user5,  user1);
    private final Follow follow7 = new Follow(user5,  user2);
    private final Follow follow8 = new Follow(user5,  user4);
    private final Follow follow9 = new Follow(user5,  user8);

    private final Follow follow10 = new Follow(user6,  user3);
    private final Follow follow11 = new Follow(user6,  user5);
    private final Follow follow12 = new Follow(user6,  user1);
    private final Follow follow13 = new Follow(user6,  user7);
    private final Follow follow14 = new Follow(user6,  user10);
    private final Follow follow15 = new Follow(user6,  user12);
    private final Follow follow16 = new Follow(user6,  user4);

    private final Status status = new Status("This is a status", null, LocalDateTime.now().toString(), null, null);

    private final String REGISTER_URL = "/register";
    private final String LOGIN_URL = "/login";
    private final String GET_FOLLOWING_URL = "/getfollowing";
    private final String LOGOUT_URL = "/logout";
    private final String FEED_URL = "/getfeed";
    private final String STORY_URL = "/getstory";
    private final String GET_FOLLOWERS_URL = "/getfollowers";
    private final String FOLLOW_URL = "/follow";
    private final String UNFOLLOW_URL = "/unfollow";

    private final List<Follow> follows = Arrays.asList(follow1, follow2, follow3, follow4, follow5, follow6,
            follow7, follow8, follow9, follow10, follow11, follow12, follow13, follow14, follow15,
            follow16);

    private ServerFacade serverFacade;


    @BeforeEach
    void setup() {
        serverFacade = new ServerFacade();

        //FollowGenerator mockFollowGenerator = Mockito.mock(FollowGenerator.class);
        //Mockito.when(mockFollowGenerator.generateUsersAndFollows(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), (FollowGenerator.Sort) Mockito.any())).thenReturn(follows);

       // Mockito.when(serverFacade.getFollowGenerator()).thenReturn(mockFollowGenerator);
        //Mockito.when(serverFacade.getFollowGenerator()).thenReturn(mockFollowGenerator);
    }

    @Test
    void testGetFollowees_noFolloweesForUser() throws IOException, TweeterRemoteException {

        FollowingRequest request = new FollowingRequest(user1, 10, null);
        FollowingResponse response = serverFacade.getFollowees(request, REGISTER_URL);

        Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() throws IOException, TweeterRemoteException {

        FollowingRequest request = new FollowingRequest(user9, 10, null);
        FollowingResponse response = serverFacade.getFollowees(request, REGISTER_URL);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() throws IOException, TweeterRemoteException {

        FollowingRequest request = new FollowingRequest(user3, 2, null);
        FollowingResponse response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() throws IOException, TweeterRemoteException {

        FollowingRequest request = new FollowingRequest(user5, 2, null);
        FollowingResponse response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user9));
        Assertions.assertTrue(response.getFollowees().contains(user11));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() throws IOException, TweeterRemoteException {

        FollowingRequest request = new FollowingRequest(user6, 2, null);
        FollowingResponse response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user10));
        Assertions.assertTrue(response.getFollowees().contains(user12));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacade.getFollowees(request, GET_FOLLOWING_URL);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void getFollowers_noFollowersForUser() throws IOException, TweeterRemoteException {

        FollowersRequest followersRequest = new FollowersRequest(user6, 10, null);
        FollowersResponse response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(0, response.getFollowers().size());
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void getFollowers_twoFollowersForUser_limitEqualUses() throws IOException, TweeterRemoteException {

        FollowersRequest followersRequest = new FollowersRequest(user5, 2, null);
        FollowersResponse response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void getFollowers_threeFollowersForUser_limitGreaterThanUsers() throws IOException, TweeterRemoteException {

        FollowersRequest followersRequest = new FollowersRequest(user1, 10, null);
        FollowersResponse followersResponse = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(3, followersResponse.getFollowers().size());
        Assertions.assertTrue(followersResponse.getFollowers().contains(user3));
        Assertions.assertTrue(followersResponse.getFollowers().contains(user5));
        Assertions.assertTrue(followersResponse.getFollowers().contains(user6));
        Assertions.assertFalse(followersResponse.getHasMorePages());

    }

    @Test
    void getFollowers_limitLessThanUsers_doesNotEndOnBoundary() throws IOException, TweeterRemoteException {

        FollowersRequest followersRequest = new FollowersRequest(user1, 2, null);
        FollowersResponse response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user3));
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        followersRequest = new FollowersRequest(user1, 2, response.getFollowers().get(1));
        response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user6));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void getFollowers_limitLessThanUsers_endsOnBoundary() throws IOException, TweeterRemoteException {

        FollowersRequest followersRequest = new FollowersRequest(user1, 1, null);
        FollowersResponse response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        followersRequest = new FollowersRequest(user1, 1, response.getFollowers().get(0));
        response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        followersRequest = new FollowersRequest(user1, 1, response.getFollowers().get(0));
        response = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user6));
        Assertions.assertFalse(response.getHasMorePages());

    }

    @Test
    void getSuccessLogin() throws IOException, TweeterRemoteException {

        LoginRequest loginRequest = new LoginRequest("Test", "Password");
        LoginResponse response = serverFacade.login(loginRequest, LOGIN_URL);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Test", response.getUser().getFirstName());
    }

    @Test
    void getFailLogin() throws IOException, TweeterRemoteException {
        LoginRequest loginRequest = null;

        LoginResponse response = serverFacade.login(loginRequest, LOGIN_URL);

        Assertions.assertFalse(response.isSuccess());

    }

    @Test
    void getSuccessRegister() throws IOException, TweeterRemoteException {

        RegisterRequest registerRequest = new RegisterRequest("Test", "User", "username", "password");
        RegisterResponse response = serverFacade.register(registerRequest, REGISTER_URL);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Test", response.getUser().getFirstName());

    }

    @Test
    void getFailRegister() throws IOException, TweeterRemoteException {

        RegisterRequest registerRequest = null;

        RegisterResponse response = serverFacade.register(registerRequest, REGISTER_URL);

        Assertions.assertFalse(response.isSuccess());

    }

    @Test
    void getSuccessfulLogout() throws IOException, TweeterRemoteException {

        LogoutRequest logoutRequest = new LogoutRequest(new User("Test", "User", ""), new AuthToken());
        LogoutResponse response = serverFacade.logout(logoutRequest, LOGOUT_URL);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getFailLogout() throws IOException, TweeterRemoteException {

        LogoutRequest logoutRequest = new LogoutRequest(new User("Test", "User", ""), new AuthToken());
       // Mockito.when(serverFacade.logout(logoutRequest)).thenReturn(new LogoutResponse(false));

        LogoutResponse response = serverFacade.logout(logoutRequest, LOGOUT_URL);

        Assertions.assertFalse(response.isSuccess());

    }



    @Test
    void followSuccess() throws IOException, TweeterRemoteException {
        FollowRequest followRequest = new FollowRequest(follow1);
        FollowResponse response = serverFacade.follow(followRequest, FOLLOW_URL);

        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    void followFail() throws IOException, TweeterRemoteException {
        FollowRequest followRequest = new FollowRequest(follow1);
        Mockito.when(serverFacade.follow(followRequest, FOLLOW_URL)).thenReturn(new FollowResponse(false));

        FollowResponse response = serverFacade.follow(followRequest, FOLLOW_URL);

        Assertions.assertFalse(response.isSuccess());
    }


    @Test
    void unfollowSuccess() throws IOException, TweeterRemoteException {
        UnfollowRequest unfollowRequest = new UnfollowRequest(follow1);
        UnfollowResponse response = serverFacade.unfollow(unfollowRequest, UNFOLLOW_URL);


        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void unfollowFail() throws IOException, TweeterRemoteException {
        UnfollowRequest unfollowRequest = new UnfollowRequest(follow1);
        Mockito.when(serverFacade.unfollow(unfollowRequest, UNFOLLOW_URL)).thenReturn(new UnfollowResponse(false));

        UnfollowResponse response = serverFacade.unfollow(unfollowRequest, UNFOLLOW_URL);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void getStory() throws IOException, TweeterRemoteException {
        StoryRequest storyRequest = new StoryRequest(user1, 10, null);
        StoryResponse response = serverFacade.getStory(storyRequest, STORY_URL);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getStoryFail() throws IOException, TweeterRemoteException {
        StoryRequest storyRequest = new StoryRequest(user1, 10, null);
        Mockito.when(serverFacade.getStory(storyRequest, STORY_URL)).thenReturn(new StoryResponse("Fail"));

        StoryResponse response = serverFacade.getStory(storyRequest, STORY_URL);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void getFeed() throws IOException, TweeterRemoteException {
        FeedRequest feedRequest = new FeedRequest(user1, 10, null);
        FeedResponse response = serverFacade.getFeed(feedRequest, FEED_URL);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getFeedFail() throws IOException, TweeterRemoteException {
        FeedRequest feedRequest = new FeedRequest(user1, 10, null);
        Mockito.when(serverFacade.getFeed(feedRequest, FEED_URL)).thenReturn(new FeedResponse("Fail"));

        FeedResponse response = serverFacade.getFeed(feedRequest, FEED_URL);

        Assertions.assertFalse(response.isSuccess());
    }









}
