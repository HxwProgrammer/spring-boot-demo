package com.example.demo.dynamo;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.example.demo.AbstractApplicationContext;
import com.example.demo.dynamodb.DynamoCommonRepository;
import com.example.demo.dynamodb.DynamoTableAttr;
import com.example.demo.dynamodb.DynamoTableAttrDefinition;
import com.example.demo.dynamodb.DynamoTableKeySchema;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateTableTest extends AbstractApplicationContext {
	private static final long readCapacityUnits = 50L;
	private static final long writeCapacityUnits = 50L;

	@Autowired
	private DynamoCommonRepository commonRepository;

	@Test
	public void createUserStatus() {
		final DynamoTableAttr tableAttr = DynamoTableAttr.builder()
				.tableName("UserStatus")
				.readCapacityUnits(readCapacityUnits)
				.writeCapacityUnits(writeCapacityUnits)
				.attrDefinitions(Arrays.asList(new DynamoTableAttrDefinition("playerId", ScalarAttributeType.S)))
				.keySchemas(Arrays.asList(new DynamoTableKeySchema("playerId", KeyType.HASH)))
				.build();

		commonRepository.createTable(tableAttr);
	}

	@Test
	public void createUserInventory() {
		final DynamoTableAttr tableAttr = DynamoTableAttr.builder()
				.tableName("UserInventory")
				.readCapacityUnits(readCapacityUnits)
				.writeCapacityUnits(writeCapacityUnits)
				.attrDefinitions(Arrays.asList(
						new DynamoTableAttrDefinition("playerId", ScalarAttributeType.S),
						new DynamoTableAttrDefinition("itemId", ScalarAttributeType.N)
				))
				.keySchemas(Arrays.asList(
						new DynamoTableKeySchema("playerId", KeyType.HASH),
						new DynamoTableKeySchema("itemId", KeyType.RANGE)
				))
				.build();

		commonRepository.createTable(tableAttr);
	}

	@Test
	public void createUserPostBox() {
		DynamoTableAttr tableAttr = DynamoTableAttr.builder()
				.tableName("UserPostBox")
				.readCapacityUnits(readCapacityUnits)
				.writeCapacityUnits(writeCapacityUnits)
				.attrDefinitions(Arrays.asList(
						new DynamoTableAttrDefinition("playerId", ScalarAttributeType.S),
						new DynamoTableAttrDefinition("messageId", ScalarAttributeType.N)
				))
				.keySchemas(Arrays.asList(
						new DynamoTableKeySchema("playerId", KeyType.HASH),
						new DynamoTableKeySchema("messageId", KeyType.RANGE)
				))
				.build();

		commonRepository.createTable(tableAttr);
	}
}
