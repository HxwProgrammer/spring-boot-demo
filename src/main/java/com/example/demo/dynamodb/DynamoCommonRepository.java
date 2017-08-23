package com.example.demo.dynamodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DynamoCommonRepository {
	@Autowired
	private DynamoDB dynamoDB;

	public Table getTable(final String tableName) {
		return dynamoDB.getTable(tableName);
	}

	public void createTable(final DynamoTableAttr tableAttr) {
		// 속성 정의
		final List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
		tableAttr.getAttrDefinitions().forEach(v -> attributeDefinitions.add(new AttributeDefinition().withAttributeName(v.getAttrName()).withAttributeType(v.getAttrType())));

		// 키 스키마
		final List<KeySchemaElement> keySchemaElements = new ArrayList<>();
		tableAttr.getKeySchemas().forEach(v -> keySchemaElements.add(new KeySchemaElement().withAttributeName(v.getAttrName()).withKeyType(v.getKeyType())));

		CreateTableRequest request = new CreateTableRequest()
				.withTableName(tableAttr.getTableName())
				.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(tableAttr.getReadCapacityUnits()).withWriteCapacityUnits(tableAttr.getWriteCapacityUnits())) // 프로비저닝 처리량 값
				.withKeySchema(keySchemaElements)
				.withAttributeDefinitions(attributeDefinitions);

		// Projection (투영) : pk, index key 로 부터 자동적으로 복사하여 추가
		// ProjectionType.KEYS_ONLY : index 와 pk 만 투영
		// ProjectionType.INCLUDE : NonKeyAttributes 에 설정 된 설정만 투영
		// ProjectionType.ALL : 모든 속성을 투영

		// Global Secondary Index
		if (tableAttr.getGlobalIndexName() != null) {
			final List<KeySchemaElement> schemaElements = new ArrayList<>();
			tableAttr.getGlobalIndexKeySchemas().forEach(v -> schemaElements.add(new KeySchemaElement().withAttributeName(v.getAttrName()).withKeyType(v.getKeyType())));

			final GlobalSecondaryIndex gsi = new GlobalSecondaryIndex()
					.withIndexName(tableAttr.getGlobalIndexName())
					.withKeySchema(schemaElements)
					.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(tableAttr.getGlobalIndexReadCapacityUnits()).withWriteCapacityUnits(tableAttr.getGlobalIndexWriteCapacityUnits()))
					.withProjection(new Projection().withProjectionType(tableAttr.getGlobalIndexProjectionType()));

			request.withGlobalSecondaryIndexes(gsi);
		}

		// Local Secondary Index
		if (tableAttr.getLocalIndexName() != null) {
			final List<KeySchemaElement> schemaElements = new ArrayList<>();
			tableAttr.getLocalIndexKeySchemas().forEach(v -> schemaElements.add(new KeySchemaElement().withAttributeName(v.getAttrName()).withKeyType(v.getKeyType())));

			final Projection projection = new Projection().withProjectionType(tableAttr.getLocalIndexProjectionType());
			if (tableAttr.getLocalIndexProjectionType() == ProjectionType.INCLUDE && CollectionUtils.isEmpty(tableAttr.getNonKeyAttributes())) {
				projection.withNonKeyAttributes(tableAttr.getNonKeyAttributes());
			}

			final LocalSecondaryIndex lsi = new LocalSecondaryIndex()
					.withIndexName(tableAttr.getLocalIndexName())
					.withKeySchema(schemaElements)
					.withProjection(projection);

			request.withLocalSecondaryIndexes(lsi);
		}

		final Table table = dynamoDB.createTable(request);

		// DynamoDB가 테이블을 생성하더라도 상태를 ACTIVE 로 설정해야만 생성된 테이블을 사용할 수 있습니다
		try {
			table.waitForActive();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void deleteTable(String tableName) {
		final Table table = dynamoDB.getTable(tableName);
		table.delete();

		try {
			table.waitForDelete();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public TableCollection<ListTablesResult> tableList() {
		return dynamoDB.listTables();
	}

}
