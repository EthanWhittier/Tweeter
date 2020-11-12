package tweeter.model.service.request;

import tweeter.model.domain.User;
import tweeter.model.service.response.PostStatusResponse;

public class PostStatusRequest {

    String status;
    User author;

    public PostStatusRequest() {}

    public PostStatusRequest(String status, User user) {
        this.status = status;
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getAuthor() {return author;}

    public void setAuthor(User user) {this.author = author;}
}
