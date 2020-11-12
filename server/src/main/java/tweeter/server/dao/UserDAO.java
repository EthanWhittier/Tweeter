package tweeter.server.dao;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.model.service.response.LogoutResponse;
import tweeter.model.service.response.RegisterResponse;

public class UserDAO {


    /**
     * This current DAO just returns dummy data.
     * Real implementation will insert/fetch to validate
     *
     *
     * //TODO Logout go here???
     */


    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse(true);
    }

    public LoginResponse login(LoginRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());
    }


    public RegisterResponse register(RegisterRequest request) {
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new RegisterResponse(user, new AuthToken());
    }

}
