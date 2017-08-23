package com.example.demo.dynamo;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.ScanResultPage;
import com.example.demo.AbstractApplicationContext;
import com.example.demo.dynamodb.UserInventoryRepository;
import com.example.demo.dynamodb.UserPostBoxRepository;
import com.example.demo.dynamodb.UserStatusRepository;
import com.example.demo.entity.UserInventory;
import com.example.demo.entity.UserPostBox;
import com.example.demo.entity.UserPostBox.PostBoxMessageType;
import com.example.demo.entity.UserStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DynamoRepositoryTest extends AbstractApplicationContext {

	private static final String playerId = "1.player";

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private UserStatusRepository userStatusRepository;

	@Autowired
	private UserInventoryRepository userInventoryRepository;

	@Autowired
	private UserPostBoxRepository userPostBoxRepository;

	@Test
	public void selectUserStatus() {
		final UserStatus us = userStatusRepository.select(playerId);
		log.info("UserStatus:{}", us);
	}

	@Test
	public void saveUserStatus() {
		final UserStatus us = UserStatus.builder()
				.playerId(playerId)
				.level(1)
				.exp(1)
				.build();
		userStatusRepository.save(us);
	}

	@Test
	public void selectAllUserInventory() {
		final List<UserInventory> userInventories = userInventoryRepository.selectAll(playerId);
		log.info("userInventories:{}", userInventories);
	}

	@Test
	public void selectUserInventory() {
		final UserInventory userInventory = userInventoryRepository.select(playerId, 1001);
		log.info("userInventory:{}", userInventory);
	}

	@Test
	public void deleteUserInventory() {
		userInventoryRepository.delete(playerId, 1001);
	}

	@Test
	public void saveUserInventory() {
		final UserInventory ui = UserInventory.builder()
				.playerId(playerId)
				.itemId(1001)
				.itemQty(100)
				.build();
		userInventoryRepository.save(ui);
	}

	@Test
	public void scanUserStatus() {
		final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		final ScanResultPage<UserStatus> scan = dynamoDBMapper.scanPage(UserStatus.class, scanExpression);

		log.info("count:{}", scan.getCount());

		int[] k = {0};
		scan.getResults().forEach(v -> log.info("[{}] {}", ++k[0], v));
	}

	@Test
	public void saveUserPostBox() {
		UserPostBox upb = UserPostBox.builder()
				.playerId(playerId)
				.messageId(System.nanoTime())
				.fromPlayerId("system.1")
				.messageTitle("system send test")
				.messageType("system")
				.attachmentItemId(0)
				.attachmentItemType(PostBoxMessageType.SYSTEM.getValue())
				.attachmentItemQty(1000L)
				.updatedTime(new Date())
				.createdTime(new Date())
				.build();
		userPostBoxRepository.save(upb);
	}

	@Test
	public void selectAll() {
		List<UserPostBox> userPostBoxes = userPostBoxRepository.selectAll(playerId);
		log.info("userPostBoxes:{}", userPostBoxes);

		userPostBoxes.forEach(v -> {
			DateTime createdTime = new DateTime(v.getCreatedTime().getTime());
			DateTime updatedTime = new DateTime(v.getUpdatedTime().getTime());
			log.info("createdTime:{}", createdTime);
			log.info("updatedTime:{}", updatedTime);
		});
	}

	@Test
	public void select() {
		UserPostBox userPostBox = userPostBoxRepository.select(playerId, 1L);
		log.info("userPostBox:{}", userPostBox);
	}

}
