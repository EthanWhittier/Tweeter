package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest {

    String status;
    User author;

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
