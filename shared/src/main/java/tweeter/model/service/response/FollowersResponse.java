package tweeter.model.service.response;

import java.util.List;

import tweeter.model.domain.User;

public class FollowersResponse extends PagedResponse {


    private List<User> followers;
    private String lastFollower;


    public FollowersResponse(String message) {super(false, message, false);}

    public FollowersResponse(List<User> followers, boolean hasMorePages, String lastFollower) {
        super(true, hasMorePages);
        this.followers = followers;
        this.lastFollower = lastFollower;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public String getLastFollower() {
        return lastFollower;
    }

    public void setLastFollower(String lastFollower) {
        this.lastFollower = lastFollower;
    }
}
