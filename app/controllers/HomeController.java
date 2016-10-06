package controllers;

import aws.SQSConnection;
import com.typesafe.config.ConfigFactory;
import play.mvc.*;

import services.ZipBuilder;
import views.html.*;
import worker.Worker;

import java.io.File;
import java.io.IOException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    public static final String AWS_S3_BUCKET = "aws.s3.bucket";
    public static final String AWS_ACCESS_KEY = "aws.access.key";
    public static final String AWS_SECRET_KEY = "aws.secret.key";

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        String accessKey = ConfigFactory.load().getString(AWS_ACCESS_KEY);
        String secretKey = ConfigFactory.load().getString(AWS_SECRET_KEY);
        String s3Bucket = ConfigFactory.load().getString(AWS_S3_BUCKET);

        Worker worker = Worker.getInstance();

        return ok(index.render(accessKey + " -- " + secretKey + " -- " + s3Bucket));


    }


}
