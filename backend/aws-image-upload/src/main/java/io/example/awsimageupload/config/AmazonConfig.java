package io.example.awsimageupload.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  @Value("${s3.AWSAccessKeyId}")
  private String accessKey;

  @Value("${s3.AWSSecretKey}")
  private String secretKey;

  @Bean
  public AmazonS3 s3() {
    AWSCredentials awsCredentials = new BasicAWSCredentials(
        accessKey,
        secretKey
    );
//    System.out.println(awsCredentials.getAWSAccessKeyId());
//    System.out.println(awsCredentials.getAWSSecretKey());

    return AmazonS3ClientBuilder
        .standard()
        .withRegion("us-east-1")
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }

}
