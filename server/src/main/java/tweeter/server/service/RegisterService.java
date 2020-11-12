package tweeter.server.service;

import java.io.IOException;

import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.RegisterServiceInterface;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.dao.UserDAO;

public class RegisterService implements RegisterServiceInterface {


    @Override
    public RegisterResponse register(RegisterRequest request) {
        return getUserDAO().register(request);
    }


   public UserDAO getUserDAO() {
        return new UserDAO();
    }
}
