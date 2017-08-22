package com.example.demand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;

import com.example.demand.DemandInfo.DemandState;
import com.example.demand.PlayerDemandInfo.PlayerDemandState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemandExample {
	public static void main(String[] args) {
		final AtomicInteger demandId = new AtomicInteger(1);

		final List<DemandInfo> demandInfoList = new ArrayList<>();
		final List<PlayerDemandInfo> playerDemandInfoList = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			final int randomItemId = new Random().nextInt(10);
			final int randomQty = new Random().nextInt(99) + 1;
			final long randomPrice = new Random().nextInt(1000) + 1000;
			final long fee = (long) (randomPrice * randomQty * 0.5);
			final String randomPlayerId = (new Random().nextInt(100) + 10000) + ".player";
			final int id = demandId.getAndAdd(1);

			demandInfoList.add(DemandInfo.builder()
					.demandId(id)
					.demandState(DemandState.REGISTER)
					.itemId(1000 + randomItemId)
					.itemQty(randomQty)
					.itemPrice(randomPrice)
					.fee(fee)
					.registrantPlayerId((randomPlayerId))
					.updatedOn(DateTime.now())
					.createdOn(DateTime.now())
					.build());

			playerDemandInfoList.add(PlayerDemandInfo.builder()
					.playerId(randomPlayerId)
					.playerDemandState(PlayerDemandState.REGISTER)
					.demandId(id)
					.registrantPlayerId(randomPlayerId)
					.updatedOn(DateTime.now())
					.createdOn(DateTime.now())
					.build());
		}

		demandInfoList.forEach(info -> log.info("info:{}", info));
		playerDemandInfoList.forEach(info -> log.info("player info:{}", info));
	}
}

@AllArgsConstructor
@Builder
@Data
class DemandInfo {
	public enum DemandState {
		REGISTER, COMPLETE, CANCEL
	}

	// 의뢰 게시판 ID
	private int demandId;

	// 의뢰 상태
	private DemandState demandState;

	// 아이템 정보
	private int itemId;
	private int itemQty;
	private long itemPrice;

	// 등록 수수료
	private long fee;

	// 의뢰 등록자
	private String registrantPlayerId;

	// 의뢰 입수자
	private String acceptPlayerId;

	private DateTime updatedOn;
	private DateTime createdOn;
}

@AllArgsConstructor
@Builder
@Data
class PlayerDemandInfo {
	public enum PlayerDemandState {
		REGISTER, COMPLETE, WAIT, TERMINATE
	}

	private String playerId;
	private PlayerDemandState playerDemandState;

	private int demandId;
	private String registrantPlayerId;

	private DateTime updatedOn;
	private DateTime createdOn;
}