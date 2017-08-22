package com.example.demanding;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class DemandingBoardInfo {
	public enum DemandingState {
		REGISTER, COMPLETE, CANCEL
	}

	// 의뢰 게시판 ID
	private int demandingId;

	// 의뢰 상태
	private DemandingState demandingState;

	// 아이템 정보
	private int itemId;
	private int itemQty;
	private long itemPrice;

	// 등록 수수료
	private long fee;

	// 의뢰 등록자
	private String register;

	// 의뢰 입수자
	private String accepter;

	private DateTime updatedTime;
	private DateTime createdTime;
}
