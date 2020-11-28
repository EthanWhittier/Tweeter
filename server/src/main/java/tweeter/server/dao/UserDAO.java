package tweeter.server.dao;


import tweeter.model.domain.AuthToken;
import tweeter.model.domain.User;
import tweeter.model.net.TweeterRemoteException;
import tweeter.model.service.request.LoginRequest;
import tweeter.model.service.request.LogoutRequest;
import tweeter.model.service.request.RegisterRequest;
import tweeter.model.service.response.LoginResponse;
import tweeter.model.service.response.LogoutResponse;
import tweeter.model.service.response.RegisterResponse;
import tweeter.server.factory.FactoryManager;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserDAO {

    private static final String tableName = "User";

    private static final String AliasAttr = "alias";
    private static final String FirstNameAttr = "firstName";
    private static final String LastNameAttr = "lastName";
    private static final String PasswordAttr = "password";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private  Table table;


    /**
     * Expires Current Users Authtoken
     *
     * @param request
     * @return
     */
    public LogoutResponse logout(LogoutRequest request) {
        return new LogoutResponse(true);
    }

    /**
     *
     * Logs a user in checking that username exists and that the
     * correct password was provided.
     *
     * @param request
     * @return
     */
    public LoginResponse login(LoginRequest request) throws TweeterRemoteException {
        table = dynamoDB.getTable(tableName);
        Item item = null;



        item = table.getItem(AliasAttr, request.getUsername());

        if(item == null) {
            throw new TweeterRemoteException("Not User exists with that username", null, null);
        }

        User user = JsonSerializer.deserialize(JsonSerializer.serialize(item.asMap()), User.class);

        if(validateCredentials(user, request)) {
            return new LoginResponse(user, getNewAuthToken());
        } else {
            throw new TweeterRemoteException("Username or password is invalid " + hash(request.getPassword()) + "!!! " + user.getPassword() , null, null);
        }

    }

    private boolean validateCredentials(User user, LoginRequest request)  {
        if(!user.getAlias().equals(request.getUsername())) {
            return false;
        }
        if(!user.getPassword().equals(hash(request.getPassword()))) {
            return false;
        }
        return true;
    }


    /**
     *
     * @param request
     * @return
     */
    public RegisterResponse register(RegisterRequest request) throws TweeterRemoteException {
        Table table = dynamoDB.getTable(tableName);

            Item item = new Item()
                    .withPrimaryKey(AliasAttr, request.getUsername())
                    .withString(FirstNameAttr, request.getFirstName())
                    .withString(LastNameAttr, request.getLastName())
                    .withString(PasswordAttr, hash(request.getPassword()));

            try {
                PutItemOutcome result = table.putItem(item, "alias <> :val", null, new ValueMap().withString(":val", request.getUsername()));
            } catch (Exception e) {
                throw new TweeterRemoteException(e.getMessage(), e.getClass().getCanonicalName(), null);
            }

         return new RegisterResponse(new User(request.getFirstName(), request.getLastName(),
                 request.getUsername(), null), getNewAuthToken());
    }


    public void deleteUser(User user) {
        Table table = dynamoDB.getTable(tableName);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(AliasAttr, user.getAlias());

        try {
            table.deleteItem(deleteItemSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private AuthToken getNewAuthToken() {
       AuthTokenDAO authTokenDAO = FactoryManager.getDAOFactory().makeAuthTokenDAO();
       return authTokenDAO.addAuthToken(UUID.randomUUID().toString());
    }

    private String hash(String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[1], 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return new String(factory.generateSecret(spec).getEncoded(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            //TODO Handle
        }
        return null;
    }

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

}
