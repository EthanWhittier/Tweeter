package tweeter.model.service.response;

import java.util.List;

import tweeter.model.domain.Status;

public class StoryResponse extends PagedResponse {


    private List<Status> userStatuses;
    private long lastStatus;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Status> userStatuses, boolean hasMorePages, long lastStatus) {
        super(true, hasMorePages);
        this.userStatuses = userStatuses;
        this.lastStatus = lastStatus;
    }


    public List<Status> getUserStatuses() {return userStatuses;}

    public long getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(long lastStatus) {
        this.lastStatus = lastStatus;
    }

    public void setUserStatuses(List<Status> userStatuses) {
        this.userStatuses = userStatuses;
    }
}
