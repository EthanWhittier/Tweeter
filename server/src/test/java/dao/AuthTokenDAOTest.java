package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import tweeter.model.domain.AuthToken;
import tweeter.model.net.TweeterRemoteException;
import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.factory.FactoryManager;

public class AuthTokenDAOTest {


    AuthTokenDAO authTokenDAO;

    @BeforeEach
    void setup() {
        authTokenDAO = FactoryManager.getDAOFactory().makeAuthTokenDAO();
    }

    @Test
    void testInsertAuthToken() throws TweeterRemoteException {
        //Insert into table
        String token = UUID.randomUUID().toString();
        authTokenDAO.addAuthToken(token);

        //Query table and compare
        AuthToken authToken = authTokenDAO.getAuthToken(token);

        Assertions.assertEquals(token, authToken.getAuthToken());

    }


    @Test
    void testDeleteAuthToken() throws TweeterRemoteException {
        //Insert into table
        String token = UUID.randomUUID().toString();
        authTokenDAO.addAuthToken(token);

        //Logout/Delete Token
        authTokenDAO.logout(token);

        //Check to make sure it is not there
        Assertions.assertNull(authTokenDAO.getAuthToken(token));


    }

}

