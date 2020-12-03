package tweeter.model.service.response;

import tweeter.model.domain.User;

public class GetUserResponse extends Response {



    private User user;
    private boolean loggedInUserFollows;


    public GetUserResponse(User user, boolean loggedInUserFollows) {
        super(true);
        this.user = user;
        this.loggedInUserFollows = loggedInUserFollows;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public void setLoggedInUserFollows(boolean loggedInUserFollows) {
        this.loggedInUserFollows = loggedInUserFollows;
    }
}
