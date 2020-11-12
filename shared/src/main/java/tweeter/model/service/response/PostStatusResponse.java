package tweeter.model.service.response;

import tweeter.model.domain.Status;

public class PostStatusResponse extends Response {

    public PostStatusResponse(String message) {
        super(false, message);
    }

    public PostStatusResponse(boolean success) {
        super(success);
    }

}
