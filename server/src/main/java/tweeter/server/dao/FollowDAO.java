package tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.Attribute;
import javax.management.Query;
import javax.xml.transform.Result;

import tweeter.model.domain.User;
import tweeter.model.service.request.FollowRequest;
import tweeter.model.service.request.FollowersRequest;
import tweeter.model.service.request.FollowingRequest;
import tweeter.model.service.request.UnfollowRequest;
import tweeter.model.service.response.FollowResponse;
import tweeter.model.service.response.FollowersResponse;
import tweeter.model.service.response.FollowingResponse;
import tweeter.model.service.response.UnfollowResponse;
import tweeter.server.factory.FactoryManager;

public class FollowDAO {



    private static final String tableName = "Follow";
    private static final String indexName = "followee-index";
    private static final String FollowerAttr = "follower";
    private static final String FolloweeAttr = "followee";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private Table table;

    public FollowResponse follow(FollowRequest request) {
        table = dynamoDB.getTable(tableName);

        Item item = new Item()
                .withPrimaryKey(FollowerAttr, request.getFollower())
                .withString(FolloweeAttr, request.getFollowee());
        try {
            table.putItem(item);
            return new FollowResponse(true);
        } catch (Exception e) {
            return new FollowResponse(false, e.getMessage());
        }

    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        table = dynamoDB.getTable(tableName);
        try {
            table.deleteItem(new PrimaryKey(FollowerAttr, request.getFollower(), FolloweeAttr, request.getFollowee()));
            return new UnfollowResponse(true);
        } catch (Exception e) {
            return new UnfollowResponse(false, e.getMessage());
        }
    }


    public boolean isFollowing(String loggedInUser, String user) {
        table = dynamoDB.getTable(tableName);
        Item item = null;


        item = table.getItem(new PrimaryKey(FollowerAttr, loggedInUser, FolloweeAttr, user));

        if(item != null) {
            return true;
        } else {
            return false;
        }

    }

    public ResultsPage getFollowersOfUserPage(String user, int pageSize, String lastFollower) {
        ResultsPage resultsPage = new ResultsPage();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#fol", FolloweeAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followee", new AttributeValue().withS(user));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withIndexName(indexName)
                .withKeyConditionExpression("#fol = :followee")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(pageSize);

        if(isNonEmptyString(lastFollower)) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(FolloweeAttr, new AttributeValue().withS(user));
            lastKey.put(FollowerAttr, new AttributeValue().withS(lastFollower));

            queryRequest = queryRequest.withExclusiveStartKey(lastKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if(items != null) {
            for(Map<String, AttributeValue> item : items) {
                String follower = item.get(FollowerAttr).getS();
                resultsPage.addValue(follower);
            }
        }

        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if(lastKey != null) {
            resultsPage.setLastKey(lastKey.get(FollowerAttr).getS());
        }

        return resultsPage;
    }


    public FollowingResponse getFolloweesOfUser(FollowingRequest request) {
        List<User> followees = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#fol", FollowerAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follower", new AttributeValue().withS(request.getFollower()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#fol = :follower")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit());

        if(isNonEmptyString(request.getLastFollowee())) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(FolloweeAttr, new AttributeValue().withS(request.getLastFollowee()));
            lastKey.put(FollowerAttr, new AttributeValue().withS(request.getFollower()));

            queryRequest = queryRequest.withExclusiveStartKey(lastKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if(items != null) {
            UserDAO userDAO = FactoryManager.getDAOFactory().makeUserDAO();
            for(Map<String, AttributeValue> item: items) {
                String followee = item.get(FolloweeAttr).getS();
                followees.add(userDAO.getUser(followee));
            }
        }

        String lastFollowee = null;
        boolean hasMorePages = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if(lastKey != null) {
            lastFollowee = lastKey.get(FolloweeAttr).getS();
            hasMorePages = true;
        }

        return new FollowingResponse(followees, hasMorePages, lastFollowee);

    }


    public FollowersResponse getFollowersOfUser(FollowersRequest request) {
        List<User> followers = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#fol", FolloweeAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followee", new AttributeValue().withS(request.getFollowee()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withIndexName(indexName)
                .withKeyConditionExpression("#fol = :followee")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit());

        if(isNonEmptyString(request.getLastFollower())) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(FolloweeAttr, new AttributeValue().withS(request.getFollowee()));
            lastKey.put(FollowerAttr, new AttributeValue().withS(request.getLastFollower()));

            queryRequest = queryRequest.withExclusiveStartKey(lastKey);
        }

        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if(items != null) {
            UserDAO userDAO = FactoryManager.getDAOFactory().makeUserDAO();
            for(Map<String, AttributeValue> item: items) {
                String follower = item.get(FollowerAttr).getS();
                followers.add(userDAO.getUser(follower));
            }
        }

        String lastFollower = null;
        boolean hasMorePages = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if(lastKey != null) {
            lastFollower = lastKey.get(FollowerAttr).getS();
            hasMorePages = true;
        }

        return new FollowersResponse(followers, hasMorePages, lastFollower);
    }

    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

    public void addFollowersBatch(List<String> followers, String FOLLOW_TARGET) {
        TableWriteItems items = new TableWriteItems(tableName);

        for(String follower: followers) {
            Item item = new Item()
                    .withPrimaryKey(FollowerAttr, follower)
                    .withString(FolloweeAttr, FOLLOW_TARGET);
            items.addItemToPut(item);


            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(tableName);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {
        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
        }
    }

}
