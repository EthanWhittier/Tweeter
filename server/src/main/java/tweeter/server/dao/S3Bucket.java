package tweeter.server.dao;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class S3Bucket {

    AmazonS3 s3;
    private final String BUCKET_NAME = "tweeter340";


    public void storeProfilePic(String userAlias, byte[] imageBytes) {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();


        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, userAlias, new ByteArrayInputStream(imageBytes), new ObjectMetadata());
        s3.putObject(putObjectRequest);

    }




}
