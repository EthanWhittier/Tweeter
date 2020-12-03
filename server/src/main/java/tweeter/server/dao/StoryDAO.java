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
import tweeter.model.service.request.PostStatusRequest;
import tweeter.model.service.request.StoryRequest;
import tweeter.model.service.response.PostStatusResponse;
import tweeter.model.service.response.StoryResponse;

public class StoryDAO {


    private static final String tableName = "Story";
    private static final String AliasAttr = "alias";
    private static final String CreatedAttr = "created";
    private static final String StatusAttr = "status";


    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    private Table table;

    public StoryResponse getStory(StoryRequest request) {
        StoryResponse storyResponse;
        List<Status> statuses = new ArrayList<>();

        Map<String, String> attrNames = new HashMap<String, String>();
        attrNames.put("#ali", AliasAttr);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":alias", new AttributeValue().withS(request.getUser().getAlias()));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(tableName)
                .withKeyConditionExpression("#ali = :alias")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues)
                .withLimit(request.getLimit())
                .withScanIndexForward(false);

        if(request.getLastCreated() != 0) {
            Map<String, AttributeValue> lastKey = new HashMap<>();
            lastKey.put(AliasAttr, new AttributeValue().withS(request.getUser().getAlias()));
            lastKey.put(CreatedAttr, new AttributeValue().withN(String.valueOf(request.getLastCreated())));

            queryRequest = queryRequest.withExclusiveStartKey(lastKey);
        }

        long lastCreated = 0;
        boolean hasMorePages = false;
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();
        if(items != null) {

            for(Map<String, AttributeValue> item : items) {
                System.out.println(item.get(CreatedAttr).getN());
                statuses.add(new Status(item.get(StatusAttr).getS(), Long.parseLong(item.get(CreatedAttr).getN())));
            }

            Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
            if(lastKey != null) {
                lastCreated = Long.parseLong(lastKey.get(CreatedAttr).getN());
                hasMorePages = true;
            }
        }

        return new StoryResponse(statuses, hasMorePages, lastCreated);

    }


    public PostStatusResponse postStatus(PostStatusRequest request, long created) {
        table = dynamoDB.getTable(tableName);

        Item item = new Item()
                .withPrimaryKey(AliasAttr, request.getAuthor().getAlias())
                .withLong(CreatedAttr, created)
                .withString(StatusAttr, request.getStatus());

        table.putItem(item);

        return new PostStatusResponse(true);

    }



    public void deleteStatus(String alias) {
        table = dynamoDB.getTable(tableName);
        table.deleteItem(AliasAttr, alias);
    }


    private static boolean isNonEmptyString(String value) {
        return (value != null && value.length() > 0);
    }

}
