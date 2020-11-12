package tweeter.model.domain;

import java.util.List;

public class Feed {

    private User user;
    private List<Status> statuses;

    public Feed(User user, List<Status> statuses) {
        this.user = user;
        this.statuses = statuses;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
