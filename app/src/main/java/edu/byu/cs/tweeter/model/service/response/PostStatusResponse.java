package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusResponse extends Response {

    private Status status;

    public PostStatusResponse(String message) {
        super(false, message);
    }

    public PostStatusResponse(boolean success, Status status) {
        super(success);
        this.status = status;

    }

    public Status getStatus() {
        return status;
    }
}
