package com.example.demo.dynamodb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper.FailedBatch;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.demo.entity.UserInventory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserInventoryRepository {
	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public void save(final UserInventory ui) {
		dynamoDBMapper.save(ui);
	}

	public void batchSave(final List<UserInventory> uis) {
		final List<FailedBatch> failedBatches = dynamoDBMapper.batchSave(uis);
		failedBatches.forEach(v -> log.info("batchSaveErrors:{}", v.getException().getMessage()));
	}

	public List<UserInventory> selectAll(final String playerId) {
		return selectAll(playerId, false);
	}

	public List<UserInventory> selectAll(final String playerId, final boolean consistent) {
		final Map<String, AttributeValue> eav = new HashMap<>();
		eav.put(":playerId", new AttributeValue().withS(playerId));
		final DynamoDBQueryExpression<UserInventory> queryExpression = new DynamoDBQueryExpression<UserInventory>()
				.withKeyConditionExpression("playerId = :playerId")
				.withExpressionAttributeValues(eav)
				//.withScanIndexForward(false)
				.withLimit(1000);

		final QueryResultPage<UserInventory> query = dynamoDBMapper.queryPage(UserInventory.class, queryExpression,
				DynamoDBMapperConfig.builder()
						.withConsistentReads((consistent) ? ConsistentReads.CONSISTENT : ConsistentReads.EVENTUAL)
						.build()
		);

		return query.getResults();
	}

	public UserInventory select(final String playerId, final int itemId) {
		return select(playerId, itemId, false);
	}

	public UserInventory select(final String playerId, final int itemId, final boolean consistent) {
		final UserInventory ui = UserInventory.builder()
				.playerId(playerId)
				.itemId(itemId)
				.build();
		return dynamoDBMapper.load(ui,
				DynamoDBMapperConfig.builder()
						.withConsistentReads((consistent) ? ConsistentReads.CONSISTENT : ConsistentReads.EVENTUAL)
						.build()
		);
	}

	public void delete(final String playerId, final int itemId) {
		final UserInventory ui = UserInventory.builder()
				.playerId(playerId)
				.itemId(itemId)
				.build();
		dynamoDBMapper.delete(ui);
	}

	public void batchDelete(final List<UserInventory> UserInventories) {
		final List<FailedBatch> failedBatches = dynamoDBMapper.batchDelete(UserInventories);
		failedBatches.forEach(v -> log.info("batchDeleteErrors:{}", v.getException().getMessage()));
	}
}
