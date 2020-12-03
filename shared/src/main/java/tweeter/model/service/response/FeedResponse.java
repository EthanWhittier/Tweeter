package tweeter.model.service.response;

import java.util.List;

import tweeter.model.domain.Feed;
import tweeter.model.domain.Status;

public class FeedResponse extends PagedResponse {

    private List<Status> statuses;
    private long lastCreatedFeed;
    private String lastUserFeed;

    public FeedResponse(String message) {
        super(false, message, false);
    }

    public FeedResponse(List<Status> statuses, boolean hasMorePages, long lastCreatedFeed, String lastUserFeed) {
        super(true, hasMorePages);
        this.statuses = statuses;
        this.lastCreatedFeed = lastCreatedFeed;
        this.lastUserFeed = lastUserFeed;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public long getLastCreatedFeed() {
        return lastCreatedFeed;
    }

    public void setLastCreatedFeed(long lastCreatedFeed) {
        this.lastCreatedFeed = lastCreatedFeed;
    }

    public String getLastUserFeed() {
        return lastUserFeed;
    }

    public void setLastUserFeed(String lastUserFeed) {
        this.lastUserFeed = lastUserFeed;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

}
