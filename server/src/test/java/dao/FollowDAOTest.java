package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import tweeter.server.dao.FollowDAO;
import tweeter.server.dao.ResultsPage;
import tweeter.server.factory.FactoryManager;

public class FollowDAOTest {



    FollowDAO followDAO;


    @BeforeEach
    void setup() {
        followDAO = FactoryManager.getDAOFactory().makeFollowDAO();
    }


    @Test
    void testGetFollowersOfUser() {
        ResultsPage results = null;
        List<String> followers = new ArrayList<>();
        while(results == null || results.hasLastKey()) {
            String lastFollower = ((results != null) ? results.getLastKey() : null);
            results = followDAO.getFollowersOfUserPage("eiwhitt", 25, lastFollower);
            followers.addAll(results.getValues());
        }
        followers.size();
    }



    @Test
    void testIfUserFollowsOther() {
        String loggedInUser = "johnny12";
        String user = "buzzyear";
        boolean follows = followDAO.isFollowing(loggedInUser, user);
        Assertions.assertTrue(follows);
    }






}
