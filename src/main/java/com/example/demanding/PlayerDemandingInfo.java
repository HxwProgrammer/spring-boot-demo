package com.example.demanding;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PlayerDemandingInfo {
	public enum PlayerDemandingState {
		REGISTER, COMPLETE, CANCEL
	}

	public enum PlayerDemandingType {
		MY, FRIEND
	}

	private String playerId;
	private int boardId;

	private int demandingId;
	private String accepter;

	private PlayerDemandingType playerDemandingType;
	private PlayerDemandingState playerDemandingState;

	private DateTime updatedTime;
	private DateTime createdTime;
}