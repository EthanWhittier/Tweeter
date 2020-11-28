package dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;


import tweeter.model.domain.AuthToken;
import tweeter.server.dao.AuthTokenDAO;
import tweeter.server.factory.FactoryManager;

public class AuthTokenDAOTest {


    AuthTokenDAO authTokenDAO;

    @BeforeEach
    void setup() {
        authTokenDAO = FactoryManager.getDAOFactory().makeAuthTokenDAO();
    }

    @Test
    void testInsertAuthToken() {
        //Insert into table
        String token = UUID.randomUUID().toString();
        authTokenDAO.addAuthToken(token);

        //Query table and compare
        AuthToken authToken = authTokenDAO.getAuthToken(token);

        Assertions.assertEquals(token, authToken.getAuthToken());

    }


}

