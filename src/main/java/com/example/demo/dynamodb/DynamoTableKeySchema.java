package com.example.demo.dynamodb;

import com.amazonaws.services.dynamodbv2.model.KeyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DynamoTableKeySchema {
	private String attrName;
	private KeyType keyType;
}
