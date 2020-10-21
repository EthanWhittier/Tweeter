package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;



public class PostStatusService {


    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.postStatus(postStatusRequest);
    }


    ServerFacade getServerFacade() {return new ServerFacade();}


}
