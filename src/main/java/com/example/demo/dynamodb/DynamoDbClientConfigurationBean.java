package com.example.demo.dynamodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * DynamoDB local link : http://docs.aws.amazon.com/ko_kr/amazondynamodb/latest/developerguide/DynamoDBLocal.html
 */
@Configuration
public class DynamoDbClientConfigurationBean {

	private static final String host = "http://localhost:8000";
	private static final String region = "us-west-2";
	private static final String accessKey = "AKIAJZ4FHZ53NQFHKQWQ";
	private static final String secretKey = "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";

	@Bean(destroyMethod = "shutdown")
	public AmazonDynamoDBAsync amazonDynamoDBAsync() {
		return AmazonDynamoDBAsyncClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(host, region))
				.build();
	}

	@Bean(destroyMethod = "shutdown")
	public DynamoDB dynamoDB(final AmazonDynamoDBAsync amazonDynamoDBAsync) {
		return new DynamoDB(amazonDynamoDBAsync);
	}

	@Bean
	public DynamoDBMapper dynamoDBMapper(final AmazonDynamoDBAsync amazonDynamoDBAsync) {
		return new DynamoDBMapper(amazonDynamoDBAsync);
	}
}
