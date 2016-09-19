package controllers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.typesafe.config.ConfigFactory;
import play.Logger;

import scala.collection.JavaConversions.*;

import java.io.File;

/**
 * Created by John on 05/09/2016.
 */
public class S3Connection  {

    public static final String AWS_S3_BUCKET = "aws.s3.bucket";
    public static final String AWS_ACCESS_KEY = "aws.access.key";
    public static final String AWS_SECRET_KEY = "aws.secret.key";

    public AmazonS3 amazonS3;

    public String s3Bucket;

    public static S3Connection instancia;

    public static S3Connection getInstance(){
        if(instancia == null){
            instancia = new S3Connection();
        }
        return instancia;

    }

    public S3Connection() {
        onStart();
    }

    public void onStart() {
        String accessKey = ConfigFactory.load().getString(AWS_ACCESS_KEY);
        String secretKey = ConfigFactory.load().getString(AWS_SECRET_KEY);
        s3Bucket = ConfigFactory.load().getString(AWS_S3_BUCKET);

        if ((accessKey != null) && (secretKey != null)) {
            AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            amazonS3 = new AmazonS3Client(awsCredentials);
            if(!amazonS3.doesBucketExist(s3Bucket))
                amazonS3.createBucket(s3Bucket);
            Logger.info("Using S3 Bucket: " + s3Bucket);
        }
    }

    public void uploadFile(File file, String fileName){
        PutObjectRequest putObjectRequest = new PutObjectRequest(s3Bucket, fileName, file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
        amazonS3.putObject(putObjectRequest); // upload file
    }


}