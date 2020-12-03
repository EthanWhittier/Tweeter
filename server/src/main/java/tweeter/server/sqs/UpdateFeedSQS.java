package tweeter.server.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class UpdateFeedSQS {


    private static final String QUEUE_URL = "https://sqs.us-west-2.amazonaws.com/851669845432/UpdateFeedQueue";

    public static void sendMessage(String message) {

        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMessageBody(message);

        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        sqs.sendMessage(request);

    }



}
