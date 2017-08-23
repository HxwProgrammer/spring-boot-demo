package com.example.demo.entity;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@DynamoDBTable(tableName = "UserPostBox")
public class UserPostBox {

	@DynamoDBHashKey
	private String playerId;

	@DynamoDBRangeKey
	private long messageId;

	private String fromPlayerId;

	private String messageTitle;

	private String messageType;

	private String messageOption;

	private int messageStatus;

	private int attachmentItemId;

	private int attachmentItemType;

	private long attachmentItemQty;

	private Date updatedTime;

	private Date createdTime;

	@Getter
	@AllArgsConstructor
	public enum PostBoxMessageType {
		SYSTEM(0), GIFT(1);
		int value;
	}
}
