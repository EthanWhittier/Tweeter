package tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tweeter.model.domain.Status;
import tweeter.model.domain.User;
import tweeter.model.service.request.FeedRequest;
import tweeter.model.service.response.FeedResponse;
import tweeter.server.factory.FactoryManager;

public class FeedDAO {


    private static final String tableName = "Feed";
    private static final String UserAttr = "user";
    private static final String StatusAttr = "status";
    private static final String AuthorAttr = "author";
    private static final String CreatedAttr = "created";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private Table table;

    public void updateFeed(String author, String status, String user, long created) {
        table = dynamoDB.getTable(tableName);

        Item item = new Item()
                .withPrimaryKey(UserAttr, user)
                .withLong(CreatedAttr, created)
                .withString(StatusAttr, status)
                .withString(AuthorAttr, author);

        table.putItem(item);
    }


    public FeedResponse getFeed(FeedRequest request) {
        FeedResponse feedResponse;
        List<Status> statuses = new ArrayList<>();
        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#usr", UserAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":user", new AttributeValue().withS(request.getUser().getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#usr = :user")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit())
                .withScanIndexForward(false);


        if(request.getLastCreatedFeed() != 0) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(UserAttr, new AttributeValue().withS(request.getLastUserFeed()));
            lastKey.put(CreatedAttr, new AttributeValue().withN(String.valueOf(request.getLastCreatedFeed())));

            queryRequest = queryRequest.withExclusiveStartKey(lastKey);
        }

        long lastCreatedFeed = 0;
        String lastUserFeed = null;
        boolean hasMorePages = false;
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);

        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if(items != null) {
            UserDAO userDAO = FactoryManager.getDAOFactory().makeUserDAO();
            for(Map<String, AttributeValue> item : items) {
                User author = userDAO.getUser(item.get(AuthorAttr).getS());
                statuses.add(new Status(author, item.get(StatusAttr).getS(), Long.parseLong(item.get(CreatedAttr).getN())));
            }

            Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
            if(lastKey != null) {
                lastUserFeed = lastKey.get(UserAttr).getS();
                lastCreatedFeed= Long.parseLong(lastKey.get(CreatedAttr).getN());
                hasMorePages = true;
            }
        }

        return new FeedResponse(statuses, hasMorePages, lastCreatedFeed, lastUserFeed);


    }





}
