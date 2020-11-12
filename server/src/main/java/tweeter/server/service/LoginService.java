package tweeter.server.service;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.LoginServiceInterface;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.server.dao.UserDAO;

public class LoginService implements LoginServiceInterface {

    @Override
    public LoginResponse login(LoginRequest request) {
        return getUserDAO().login(request);
    }


    public UserDAO getUserDAO() {
        return new UserDAO();
    }

}