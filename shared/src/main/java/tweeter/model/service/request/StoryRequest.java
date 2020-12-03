package tweeter.model.service.request;

import tweeter.model.domain.Status;
import tweeter.model.domain.Story;
import tweeter.model.domain.User;
import tweeter.model.service.response.StoryResponse;

public class StoryRequest {


    private User user;
    private int limit;
    private long lastCreated;

    public StoryRequest() {}

    public StoryRequest(User user, int limit, long lastCreated) {
        this.user = user;
        this.limit = limit;
        this.lastCreated = lastCreated;
    }

    public User getUser() {
        return user;
    }

    public int getLimit() {
        return limit;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getLastCreated() {
        return lastCreated;
    }

    public void setLastCreated(long lastCreated) {
        this.lastCreated = lastCreated;
    }


}
