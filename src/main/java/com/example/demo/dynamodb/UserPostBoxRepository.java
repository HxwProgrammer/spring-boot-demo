package com.example.demo.dynamodb;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper.FailedBatch;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.entity.UserPostBox;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserPostBoxRepository {
	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public void save(final UserPostBox upb) {
		dynamoDBMapper.save(upb);
	}

	public void batchSave(final List<UserPostBox> uis) {
		final List<FailedBatch> failedBatches = dynamoDBMapper.batchSave(uis);
		failedBatches.forEach(v -> log.info("batchSaveErrors:{}", v.getException().getMessage()));
	}

	public List<UserPostBox> selectAll(final String playerId) {
		return selectAll(playerId, false);
	}

	public List<UserPostBox> selectAll(final String playerId, final boolean consistent) {
		final HashMap<String, AttributeValue> eav = new HashMap<>();
		eav.put(":playerId", new AttributeValue().withS(playerId));
		final DynamoDBQueryExpression<UserPostBox> queryExpression = new DynamoDBQueryExpression<UserPostBox>()
				.withKeyConditionExpression("playerId = :playerId")
				.withExpressionAttributeValues(eav)
				.withScanIndexForward(false)
				.withLimit(100);

		final QueryResultPage<UserPostBox> query = dynamoDBMapper.queryPage(UserPostBox.class, queryExpression,
				DynamoDBMapperConfig.builder()
						.withConsistentReads((consistent) ? ConsistentReads.CONSISTENT : ConsistentReads.EVENTUAL)
						.build()
		);

		return query.getResults();
	}

	public UserPostBox select(final String playerId, final long messageId) {
		return select(playerId, messageId, false);
	}

	public UserPostBox select(final String playerId, final long messageId, final boolean consistent) {
		final UserPostBox upb = UserPostBox.builder()
				.playerId(playerId)
				.messageId(messageId)
				.build();
		return dynamoDBMapper.load(upb,
				DynamoDBMapperConfig.builder()
						.withConsistentReads((consistent) ? ConsistentReads.CONSISTENT : ConsistentReads.EVENTUAL)
						.build()
		);
	}

	public void delete(final String playerId, final long messageId) {
		final UserPostBox upb = UserPostBox.builder()
				.playerId(playerId)
				.messageId(messageId)
				.build();
		dynamoDBMapper.delete(upb);
	}

	public void batchDelete(final List<UserPostBox> userPostBoxes) {
		final List<FailedBatch> failedBatches = dynamoDBMapper.batchDelete(userPostBoxes);
		failedBatches.forEach(v -> log.info("batchDeleteErrors:{}", v.getException().getMessage()));
	}
}
