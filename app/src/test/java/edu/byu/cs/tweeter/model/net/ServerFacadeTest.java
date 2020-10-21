package edu.byu.cs.tweeter.model.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Status;
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

    private final Status status = new Status("This is a status", null, LocalDateTime.now(), null, null);


    private final List<Follow> follows = Arrays.asList(follow1, follow2, follow3, follow4, follow5, follow6,
            follow7, follow8, follow9, follow10, follow11, follow12, follow13, follow14, follow15,
            follow16);

    private ServerFacade serverFacadeSpy;

    @BeforeEach
    void setup() {
        serverFacadeSpy = Mockito.spy(new ServerFacade());

        FollowGenerator mockFollowGenerator = Mockito.mock(FollowGenerator.class);
        Mockito.when(mockFollowGenerator.generateUsersAndFollows(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), (FollowGenerator.Sort) Mockito.any())).thenReturn(follows);

        Mockito.when(serverFacadeSpy.getFollowGenerator()).thenReturn(mockFollowGenerator);
        Mockito.when(serverFacadeSpy.getFollowGenerator()).thenReturn(mockFollowGenerator);
    }

    @Test
    void testGetFollowees_noFolloweesForUser() {

        FollowingRequest request = new FollowingRequest(user1, 10, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(0, response.getFollowees().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_oneFollowerForUser_limitGreaterThanUsers() {

        FollowingRequest request = new FollowingRequest(user9, 10, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_twoFollowersForUser_limitEqualsUsers() {

        FollowingRequest request = new FollowingRequest(user3, 2, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void testGetFollowees_limitLessThanUsers_endsOnPageBoundary() {

        FollowingRequest request = new FollowingRequest(user5, 2, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user9));
        Assertions.assertTrue(response.getFollowees().contains(user11));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user2));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user5, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertTrue(response.getFollowees().contains(user8));
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void testGetFollowees_limitLessThanUsers_notEndsOnPageBoundary() {

        FollowingRequest request = new FollowingRequest(user6, 2, null);
        FollowingResponse response = serverFacadeSpy.getFollowees(request);

        // Verify first page
        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user3));
        Assertions.assertTrue(response.getFollowees().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify second page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user1));
        Assertions.assertTrue(response.getFollowees().contains(user7));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify third page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(2, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user10));
        Assertions.assertTrue(response.getFollowees().contains(user12));
        Assertions.assertTrue(response.getHasMorePages());

        // Get and verify fourth page
        request = new FollowingRequest(user6, 2, response.getFollowees().get(1));
        response = serverFacadeSpy.getFollowees(request);

        Assertions.assertEquals(1, response.getFollowees().size());
        Assertions.assertTrue(response.getFollowees().contains(user4));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void getFollowers_noFollowersForUser() {

        FollowersRequest followersRequest = new FollowersRequest(user6, 10, null);
        FollowersResponse response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(0, response.getFollowers().size());
        Assertions.assertFalse(response.getHasMorePages());
    }


    @Test
    void getFollowers_twoFollowersForUser_limitEqualUses() {

        FollowersRequest followersRequest = new FollowersRequest(user5, 2, null);
        FollowersResponse response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void getFollowers_threeFollowersForUser_limitGreaterThanUsers() {

        FollowersRequest followersRequest = new FollowersRequest(user1, 10, null);
        FollowersResponse followersResponse = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(3, followersResponse.getFollowers().size());
        Assertions.assertTrue(followersResponse.getFollowers().contains(user3));
        Assertions.assertTrue(followersResponse.getFollowers().contains(user5));
        Assertions.assertTrue(followersResponse.getFollowers().contains(user6));
        Assertions.assertFalse(followersResponse.getHasMorePages());

    }

    @Test
    void getFollowers_limitLessThanUsers_doesNotEndOnBoundary() {

        FollowersRequest followersRequest = new FollowersRequest(user1, 2, null);
        FollowersResponse response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(2, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user3));
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        followersRequest = new FollowersRequest(user1, 2, response.getFollowers().get(1));
        response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user6));
        Assertions.assertFalse(response.getHasMorePages());
    }

    @Test
    void getFollowers_limitLessThanUsers_endsOnBoundary() {

        FollowersRequest followersRequest = new FollowersRequest(user1, 1, null);
        FollowersResponse response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user3));
        Assertions.assertTrue(response.getHasMorePages());

        followersRequest = new FollowersRequest(user1, 1, response.getFollowers().get(0));
        response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user5));
        Assertions.assertTrue(response.getHasMorePages());

        followersRequest = new FollowersRequest(user1, 1, response.getFollowers().get(0));
        response = serverFacadeSpy.getFollowers(followersRequest);

        Assertions.assertEquals(1, response.getFollowers().size());
        Assertions.assertTrue(response.getFollowers().contains(user6));
        Assertions.assertFalse(response.getHasMorePages());

    }

    @Test
    void getSuccessLogin() {

        LoginRequest loginRequest = new LoginRequest("Test", "Password");
        LoginResponse response = serverFacadeSpy.login(loginRequest);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Test", response.getUser().getFirstName());
    }

    @Test
    void getFailLogin() {
        LoginRequest loginRequest = new LoginRequest("Test", "Password");

        //Mock data, easily switched when testing real data.
        Mockito.when(serverFacadeSpy.login(loginRequest)).thenReturn(new LoginResponse("TestFail"));

        LoginResponse response = serverFacadeSpy.login(loginRequest);

        Assertions.assertFalse(response.isSuccess());

    }

    @Test
    void getSuccessRegister() {

        RegisterRequest registerRequest = new RegisterRequest("Test", "User", "username", "password");
        RegisterResponse response = serverFacadeSpy.register(registerRequest);

        Assertions.assertTrue(response.isSuccess());
        Assertions.assertEquals("Test", response.getUser().getFirstName());
    }

    @Test
    void getFailRegister() {

        RegisterRequest registerRequest = new RegisterRequest("Test", "User", "username", "password");
        Mockito.when(serverFacadeSpy.register(registerRequest)).thenReturn(new RegisterResponse("TestFail"));

        RegisterResponse response = serverFacadeSpy.register(registerRequest);

        Assertions.assertFalse(response.isSuccess());

    }

    @Test
    void getSuccessfulLogout() {

        LogoutRequest logoutRequest = new LogoutRequest(new User("Test", "User", ""), new AuthToken());
        LogoutResponse response = serverFacadeSpy.logout(logoutRequest);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getFailLogout() {

        LogoutRequest logoutRequest = new LogoutRequest(new User("Test", "User", ""), new AuthToken());
        Mockito.when(serverFacadeSpy.logout(logoutRequest)).thenReturn(new LogoutResponse(false));

        LogoutResponse response = serverFacadeSpy.logout(logoutRequest);

        Assertions.assertFalse(response.isSuccess());

    }

    @Test
    void getPostStatus() {

        PostStatusRequest postStatusRequest = new PostStatusRequest("This is a status", user1);
        PostStatusResponse response = serverFacadeSpy.postStatus(postStatusRequest);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getPostStatusFail() {

        PostStatusRequest postStatusRequest = new PostStatusRequest("This is a status", user1);

        Mockito.when(serverFacadeSpy.postStatus(postStatusRequest)).thenReturn(new PostStatusResponse("Fail"));
        PostStatusResponse response = serverFacadeSpy.postStatus(postStatusRequest);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void followSuccess() {
        FollowRequest followRequest = new FollowRequest(follow1);
        FollowResponse response = serverFacadeSpy.follow(followRequest);

        Assertions.assertTrue(response.isSuccess());

    }

    @Test
    void followFail() {
        FollowRequest followRequest = new FollowRequest(follow1);
        Mockito.when(serverFacadeSpy.follow(followRequest)).thenReturn(new FollowResponse(false));

        FollowResponse response = serverFacadeSpy.follow(followRequest);

        Assertions.assertFalse(response.isSuccess());
    }


    @Test
    void unfollowSuccess() {
        UnfollowRequest unfollowRequest = new UnfollowRequest(follow1);
        UnfollowResponse response = serverFacadeSpy.unfollow(unfollowRequest);


        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void unfollowFail() {
        UnfollowRequest unfollowRequest = new UnfollowRequest(follow1);
        Mockito.when(serverFacadeSpy.unfollow(unfollowRequest)).thenReturn(new UnfollowResponse(false));

        UnfollowResponse response = serverFacadeSpy.unfollow(unfollowRequest);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void getStory() {
        StoryRequest storyRequest = new StoryRequest(user1, 10, null);
        StoryResponse response = serverFacadeSpy.getStory(storyRequest);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getStoryFail() {
        StoryRequest storyRequest = new StoryRequest(user1, 10, null);
        Mockito.when(serverFacadeSpy.getStory(storyRequest)).thenReturn(new StoryResponse("Fail"));

        StoryResponse response = serverFacadeSpy.getStory(storyRequest);

        Assertions.assertFalse(response.isSuccess());
    }

    @Test
    void getFeed() {
        FeedRequest feedRequest = new FeedRequest(user1, 10, null);
        FeedResponse response = serverFacadeSpy.getFeed(feedRequest);

        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    void getFeedFail() {
        FeedRequest feedRequest = new FeedRequest(user1, 10, null);
        Mockito.when(serverFacadeSpy.getFeed(feedRequest)).thenReturn(new FeedResponse("Fail"));

        FeedResponse response = serverFacadeSpy.getFeed(feedRequest);

        Assertions.assertFalse(response.isSuccess());
    }









}
