package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.dao.UserDAO;
import tweeter.server.factory.FactoryManager;


public class UserDAOTest {

    AuthTokenDAO authTokenDAO;
    UserDAO userDAO;
    RegisterRequest request;
    RegisterResponse response;
    User testUser;


    @BeforeEach
    void setup() {
        userDAO = FactoryManager.getDAOFactory().makeUserDAO();
        request = new RegisterRequest("Test", "User", "testuser", "password");
        testUser = new User(request.getFirstName(), request.getLastName(),
                request.getUsername(), null);
        response = new RegisterResponse(testUser, new AuthToken());
    }

    @AfterEach
    public void tearDown() {
        userDAO.deleteUser(testUser);
    }


    @Test
    void testRegisterSuccess() throws TweeterRemoteException {
        RegisterResponse testResponse = userDAO.register(request);
        Assertions.assertEquals(response.getUser(), testResponse.getUser());
    }

    @Test
    void testRegisterFail_AlreadyRegisteredUsername() throws TweeterRemoteException {
        //Register a User
        userDAO.register(request);
        //Attempt to register same User, but throw exception
        Assertions.assertThrows(TweeterRemoteException.class, () -> {userDAO.register(request);});
    }


    @Test
    void testLoginSuccess() throws TweeterRemoteException {
        User testUser = userDAO.register(request).getUser();
        LoginResponse testResponse = new LoginResponse(testUser, new AuthToken());
        LoginResponse response = userDAO.login(new LoginRequest("testuser", "password"));
        Assertions.assertEquals(testResponse.getUser(), response.getUser());
    }


    @Test
    void testLoginFail_UsernameDoesNotExist() throws TweeterRemoteException {
        User testUser = userDAO.register(request).getUser();
        LoginResponse testResponse = new LoginResponse(testUser, new AuthToken());
        Assertions.assertThrows(TweeterRemoteException.class, () -> {userDAO.login(new LoginRequest("invalid", "usernameAndPassword"));});
    }


    @Test
    void testLoginFail_IncorrectPassword() throws TweeterRemoteException {
        User testUser = userDAO.register(request).getUser();
        Assertions.assertThrows(TweeterRemoteException.class, () -> {userDAO.login(new LoginRequest("testuser", "invalid"));});
    }






}
