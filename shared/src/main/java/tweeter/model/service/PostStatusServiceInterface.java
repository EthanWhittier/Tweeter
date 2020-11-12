package tweeter.model.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.response.PostStatusResponse;

public interface PostStatusServiceInterface  {

    PostStatusResponse postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException;


}
