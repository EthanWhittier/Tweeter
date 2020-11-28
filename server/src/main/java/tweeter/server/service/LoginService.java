package tweeter.server.service;

import com.amazonaws.services.lambda.runtime.Context;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.LoginServiceInterface;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.server.dao.UserDAO;
import tweeter.server.factory.FactoryManager;

public class LoginService implements LoginServiceInterface {

    @Override
    public LoginResponse login(LoginRequest request) throws TweeterRemoteException {
        LoginResponse response;
        try {
            response = getUserDAO().login(request);
        } catch (Exception e) {
            throw new TweeterRemoteException(e.getMessage(), null, null);
        }

        return response;
    }


    public UserDAO getUserDAO() {
        return FactoryManager.getDAOFactory().makeUserDAO();
    }

}