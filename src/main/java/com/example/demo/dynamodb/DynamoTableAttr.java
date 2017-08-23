package com.example.demo.dynamodb;

import java.util.List;

import com.amazonaws.services.dynamodbv2.model.ProjectionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DynamoTableAttr {
	private String tableName;
	private long readCapacityUnits;
	private long writeCapacityUnits;

	private List<DynamoTableAttrDefinition> attrDefinitions;
	private List<DynamoTableKeySchema> keySchemas;

	private String globalIndexName;
	private long globalIndexReadCapacityUnits;
	private long globalIndexWriteCapacityUnits;
	private ProjectionType globalIndexProjectionType;
	private List<DynamoTableKeySchema> globalIndexKeySchemas;

	private String localIndexName;
	private ProjectionType localIndexProjectionType;
	private List<DynamoTableKeySchema> localIndexKeySchemas;
	private List<String> nonKeyAttributes;
}
