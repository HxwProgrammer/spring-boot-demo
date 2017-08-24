package com.example.demanding;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PlayerDemandInfo {
	public enum PlayerDemandState {
		REGISTER, COMPLETE, CANCEL
	}

	public enum PlayerDemandType {
		MY, FRIEND
	}

	private String playerId;
	private int boardId;

	private int demandId;
	private String accepter;

	private PlayerDemandType playerDemandType;
	private PlayerDemandState playerDemandState;

	private DateTime updatedTime;
	private DateTime createdTime;
}