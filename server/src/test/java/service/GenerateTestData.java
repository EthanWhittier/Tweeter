package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.UserDAO;
import tweeter.server.dao.fill.Filler;
import tweeter.server.factory.FactoryManager;

public class GenerateTestData {




    AuthTokenDAO authTokenDAO;
    UserDAO userDAO;
    RegisterRequest request;
    RegisterResponse response;
    User testUser;



    @BeforeEach
    void setup() {
        userDAO = FactoryManager.getDAOFactory().makeUserDAO();
        request = new RegisterRequest("Ethan", "Whittier", "eiwhitt", "password");
        testUser = new User(request.getFirstName(), request.getLastName(),
                request.getUsername(), null);
        response = new RegisterResponse(testUser, new AuthToken());
    }

    @Test
    void registerUser() throws TweeterRemoteException {
        userDAO.register(request);
    }


    @Test
    void fillDatabase() {
        Filler.fillDatabase();
    }




}
