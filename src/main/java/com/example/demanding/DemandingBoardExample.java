package com.example.demanding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;

import com.example.demanding.DemandingBoardInfo.DemandingState;
import com.example.demanding.PlayerDemandingInfo.PlayerDemandingState;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemandingBoardExample {
	public static void main(String[] args) {
		final AtomicInteger demandingId = new AtomicInteger(1);

		final List<DemandingBoardInfo> demandingBoardInfoList = new ArrayList<>();
		final List<PlayerDemandingInfo> playerDemandingInfoList = new ArrayList<>();

		for (int i = 0; i < 50; i++) {
			final int randomItemId = new Random().nextInt(10);
			final int randomQty = new Random().nextInt(99) + 1;
			final long randomPrice = new Random().nextInt(1000) + 1000;
			final long fee = (long) (randomPrice * randomQty * 0.5);
			final String randomPlayerId = (new Random().nextInt(100) + 10000) + ".player";
			final int id = demandingId.getAndAdd(1);

			demandingBoardInfoList.add(DemandingBoardInfo.builder()
					.demandingId(id)
					.demandingState(DemandingState.REGISTER)
					.itemId(1000 + randomItemId)
					.itemQty(randomQty)
					.itemPrice(randomPrice)
					.fee(fee)
					.register((randomPlayerId))
					.updatedTime(DateTime.now())
					.createdTime(DateTime.now())
					.build());

			playerDemandingInfoList.add(PlayerDemandingInfo.builder()
					.register(randomPlayerId)
					.playerDemandingState(PlayerDemandingState.REGISTER)
					.demandingId(id)
					.accepter(randomPlayerId)
					.updatedTime(DateTime.now())
					.createdTime(DateTime.now())
					.build());
		}

		demandingBoardInfoList.forEach(info -> log.info("info:{}", info));
		playerDemandingInfoList.forEach(info -> log.info("player info:{}", info));
	}
}