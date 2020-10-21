package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LogoutRequest  {

    private User user;
    private AuthToken authToken;


    public LogoutRequest(User user, AuthToken authToken) {
        this.user = user;
        this.authToken = authToken;
    }


}
