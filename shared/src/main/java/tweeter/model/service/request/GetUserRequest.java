package tweeter.model.service.request;

public class GetUserRequest {



    private String user;
    private String loggedInUser;

    public GetUserRequest() {}

    public GetUserRequest(String user, String loggedInUser) {
        this.user = user;
        this.loggedInUser = loggedInUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
