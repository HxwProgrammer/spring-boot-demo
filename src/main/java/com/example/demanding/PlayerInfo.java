package com.example.demanding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PlayerInfo {
	private String playerId;
	private long gold;
	private long exp;

	public void increaseGold(long amount) {
		gold += amount;
	}

	public void decreaseGold(long amount) {
		gold -= amount;
	}

	public void increaseExp(long amount) {
		exp += amount;
	}

	public void decreaseExp(long amount) {
		exp -= amount;
	}
}
