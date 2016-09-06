package controllers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import play.Logger;

import scala.collection.JavaConversions.*;

/**
 * Created by John on 05/09/2016.
 */
public class S3Connection {

    public AmazonS3 amazonS3;



    public void conectar() {

//        if ((accessKey != null) && (secretKey != null)) {
//            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
//            amazonS3 = new AmazonS3Client(awsCredentials);
//            amazonS3.createBucket(s3Bucket);
//            Logger.info("Using S3 Bucket: " + s3Bucket);
//        }
    }
}
