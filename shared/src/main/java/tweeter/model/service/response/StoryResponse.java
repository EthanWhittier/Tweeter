package tweeter.model.service.response;

import java.util.List;

import tweeter.model.domain.Status;

public class StoryResponse extends PagedResponse {


    private List<Status> userStatuses;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Status> userStatuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.userStatuses = userStatuses;
    }

    public List<Status> getUserStatuses() {return userStatuses;}



}
