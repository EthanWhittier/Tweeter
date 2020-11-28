package edu.byu.cs.client;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;

public class DataCache {

    private static DataCache dataCache;
    private User loggedInUser;
    private AuthToken authToken;

    public static DataCache getInstance() {
        if (dataCache == null) {
            dataCache = new DataCache();
            return dataCache;
        } else {
            return dataCache;
        }

    }

    private DataCache() {}

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
