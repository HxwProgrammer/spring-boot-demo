package com.example.demandboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class PlayerInventory {
	private String playerId;
	private int itemId;
	private int itemQty;

	public void decrease(int qty) {
		itemQty -= qty;
	}

	public void increase(int qty) {
		itemQty += qty;
	}
}
