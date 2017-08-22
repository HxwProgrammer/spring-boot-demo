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
		REGISTER, COMPLETE, WAIT, TERMINATE
	}

	private String register;
	private PlayerDemandingState playerDemandingState;

	private int demandingId;
	private String accepter;

	private DateTime updatedTime;
	private DateTime createdTime;
}