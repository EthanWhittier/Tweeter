package edu.byu.cs.client.model.service;

import java.io.IOException;

import edu.byu.cs.client.model.net.ServerFacade;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.PostStatusServiceInterface;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;



public class PostStatusServiceProxy implements PostStatusServiceInterface {

    private final static String POST_STATUS_URL = "/poststatus";

    public PostStatusResponse postStatus(PostStatusRequest postStatusRequest) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        return serverFacade.postStatus(postStatusRequest, POST_STATUS_URL);
    }


    ServerFacade getServerFacade() {return new ServerFacade();}


}
