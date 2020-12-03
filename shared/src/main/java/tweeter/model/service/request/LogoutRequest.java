package tweeter.model.service.request;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;

public class LogoutRequest  {

    private User user;
    private AuthToken authToken;


    public LogoutRequest() {}

    public LogoutRequest(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
