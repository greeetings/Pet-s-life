package org.example.pet.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.S3AccessPointResource;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.FileUtils;
import org.example.pet.service.S3Services;
import org.example.pet.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class S3ServicesImpl implements S3Services {

    private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

    @Autowired
    private AmazonS3 s3client;



    @Value("${jsa.s3.bucket}")
    private String bucketName;

    @Override
    public void downloadFile(String keyName) {

        try {

            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(
                    bucketName, keyName));



            String temp;
            temp = s3object.getRedirectLocation();
            System.out.println(temp);

            S3ObjectInputStream inputStream = s3object.getObjectContent();

            FileUtils.copyInputStreamToFile(inputStream, new File("/D:/IntellijIdea/test2.jpg"));
            System.out.println("Content-Type: "  +
                    s3object.getObjectMetadata().getContentType());
            Utility.displayText(s3object.getObjectContent());
            logger.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException ioe) {
            logger.info("IOE Error Message: " + ioe.getMessage());
        }
    }

    @Override
    public String uploadFile(String keyName, MultipartFile file) {

        try {

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(),metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.println(bucketName);
            System.out.println(keyName);

            String path = "https://" + bucketName + ".s3.amazonaws.com/" + keyName;
            return path;



        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            logger.info("Osel");
        }
        return "null";
    }

}
//Image image = ImageIO.read(inputStream);