package com.example.demo.dynamodb;

import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DynamoTableAttrDefinition {
	private String attrName;
	private ScalarAttributeType attrType;
}
