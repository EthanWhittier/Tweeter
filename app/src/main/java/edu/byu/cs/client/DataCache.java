package edu.byu.cs.client;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;

public class DataCache {

    private static DataCache dataCache;
    private User loggedInUser;
    private AuthToken authToken;
    private long lastCreatedStory;
    private long lastCreatedFeed;
    private User currentProfile;

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

    public long getLastCreatedStory() {
        return lastCreatedStory;
    }

    public void setLastCreatedStory(long lastCreatedStory) {
        this.lastCreatedStory = lastCreatedStory;
    }

    public long getLastCreatedFeed() {
        return lastCreatedFeed;
    }

    public void setLastCreatedFeed(long lastCreatedFeed) {
        this.lastCreatedFeed = lastCreatedFeed;
    }

    public static DataCache getDataCache() {
        return dataCache;
    }

    public static void setDataCache(DataCache dataCache) {
        DataCache.dataCache = dataCache;
    }

    public User getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(User currentProfile) {
        this.currentProfile = currentProfile;
    }
}
