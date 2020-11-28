package tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.*;

import tweeter.model.domain.AuthToken;

public class AuthTokenDAO {

    private  AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);


    private final String TableName = "AuthToken";

    private final String TokenAttr = "auth_token";
    private final String CreatedAttr = "created";


    public AuthToken addAuthToken(String authToken) {
        Table table = dynamoDB.getTable(TableName);

        Item item = new Item().withPrimaryKey(TokenAttr, authToken)
                .withString(CreatedAttr, String.valueOf(System.currentTimeMillis()));

        table.putItem(item);

        String json = JsonSerializer.serialize(item.asMap());
        return JsonSerializer.deserialize(json, AuthToken.class);

    }


    public AuthToken getAuthToken(String authToken) {
        Table table = dynamoDB.getTable(TableName);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("auth_token = :nn")
                .withValueMap(new ValueMap()
                .withString(":nn", authToken));

        ItemCollection<QueryOutcome> items = table.query(spec);

        for(Item item: items) {
            String json = JsonSerializer.serialize(item.asMap());
            return JsonSerializer.deserialize(json, AuthToken.class);
        }

        return null;
    }


}
