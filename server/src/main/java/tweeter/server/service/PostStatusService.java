package tweeter.server.service;

import tweeter.model.service.PostStatusServiceInterface;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.server.dao.StatusDAO;

public class PostStatusService implements PostStatusServiceInterface {


    @Override
    public PostStatusResponse postStatus(PostStatusRequest request) {
        return getStatusDAO().postStatus(request);
    }

    public StatusDAO getStatusDAO() {
        return new StatusDAO();
    }
}
