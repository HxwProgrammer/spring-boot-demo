package com.example.demo.dynamodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.example.demo.entity.UserStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserStatusRepository {
	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	public void save(final UserStatus us) {
		dynamoDBMapper.save(us);
	}

	public UserStatus select(final String playerId) {
		return select(playerId, false);
	}

	public UserStatus select(final String playerId, final boolean consistent) {
		return dynamoDBMapper.load(UserStatus.class, playerId,
				DynamoDBMapperConfig.builder()
						.withConsistentReads((consistent) ? ConsistentReads.CONSISTENT : ConsistentReads.EVENTUAL)
						.build()
		);
	}
}
