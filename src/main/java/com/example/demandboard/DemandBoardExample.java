package com.example.demandboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;

import com.example.demandboard.DemandBoardInfo.DemandState;
import com.example.demandboard.PlayerDemandInfo.PlayerDemandState;
import com.example.demandboard.PlayerDemandInfo.PlayerDemandType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemandBoardExample {
	public static void main(String[] args) {
		final AtomicInteger demandId = new AtomicInteger(1);

		final List<DemandBoardInfo> demandBoardInfoList = new ArrayList<>();

		// 1. 1.player 의뢰 게시판에 등록
		// 2. 2.player 가 1.player 의 의뢰 입수
		// 3. 2.player 가 아이템 납품
		// 4. 1.player 가 아이템 획득 및 수수료 지불

		final int firstId = demandId.getAndAdd(1);

		// 플레이어 재화 및 아이템 설정
		final String firstPlayerId = "1.player";
		final PlayerInfo firstPlayerInfo = PlayerInfo.builder().playerId(firstPlayerId).gold(100000).exp(0).build();
		final Map<Integer, PlayerInventory> firstPlayerInventory = new HashMap<>();
		firstPlayerInventory.put(1001, PlayerInventory.builder().playerId(firstPlayerId).itemId(1001).itemQty(100).build());

		log.info("firstPlayerInfo:{}", firstPlayerInfo);
		log.info("firstPlayerInventory:{}", firstPlayerInventory);

		final String secondPlayerId = "2.player";
		final PlayerInfo secondPlayerInfo = PlayerInfo.builder().playerId(secondPlayerId).gold(100000).exp(0).build();
		final Map<Integer, PlayerInventory> secondPlayerInventory = new HashMap<>();
		secondPlayerInventory.put(1001, PlayerInventory.builder().playerId(firstPlayerId).itemId(1001).itemQty(100).build());

		log.info("secondPlayerInfo:{}", secondPlayerInfo);
		log.info("secondPlayerInventory:{}", secondPlayerInventory);

		// 1.player 의뢰 게시판에 등록
		final DemandDto demandDto = addDemand(firstPlayerId, firstId);
		demandBoardInfoList.add(demandDto.getDemandBoardInfo());

		demandDto.getDemandBoardInfo().setAccepter(secondPlayerId);
		demandDto.getDemandBoardInfo().setUpdatedTime(DateTime.now());

		// 2.player 가 1.player 의 의뢰 입수
		final PlayerDemandInfo acquireDemandInfo = acquireDemand(secondPlayerId, demandDto.getDemandBoardInfo());

		// 2.player 가 아이템 납품 및 골드 입수
		secondPlayerInventory.get(demandDto.getDemandBoardInfo().getItemId()).decrease(demandDto.getDemandBoardInfo().getItemQty());
		secondPlayerInfo.increaseGold(demandDto.getDemandBoardInfo().getItemPrice() * demandDto.getDemandBoardInfo().getItemQty() - demandDto.getDemandBoardInfo().getFee());

		// 4. 1.player 아이템 획득, 금액 및 수수료 지불
		firstPlayerInventory.get(demandDto.getDemandBoardInfo().getItemId()).increase(demandDto.getDemandBoardInfo().getItemQty());
		firstPlayerInfo.decreaseGold(demandDto.getDemandBoardInfo().getItemPrice() * demandDto.getDemandBoardInfo().getItemQty() + demandDto.getDemandBoardInfo().getFee());

		onCompleteAll(demandDto, acquireDemandInfo);

		log.info("amount:{}", demandDto.getDemandBoardInfo().getFee() * demandDto.getDemandBoardInfo().getItemQty());

		log.info("after firstPlayerInfo:{}", firstPlayerInfo);
		log.info("after firstPlayerInventory:{}", firstPlayerInventory);
		log.info("after secondPlayerInfo:{}", secondPlayerInfo);
		log.info("after secondPlayerInventory:{}", secondPlayerInventory);

		log.info("demandBoardInfoList:{}", demandBoardInfoList);
		log.info("firstPlayer PlayerDemandInfo:{}", demandDto.getPlayerDemandInfo());
		log.info("secondPlayer acquireDemandInfo:{}", acquireDemandInfo);
	}

	private static DemandDto addDemand(final String playerId, final int demandId) {
		final PlayerDemandInfo playerDemandInfo = PlayerDemandInfo.builder()
				.playerId(playerId)
				.playerDemandType(PlayerDemandType.MY)
				.playerDemandState(PlayerDemandState.REGISTER)
				.demandId(demandId)
				.accepter(playerId)
				.updatedTime(DateTime.now())
				.createdTime(DateTime.now())
				.build();

		final DemandBoardInfo demandBoardInfo = DemandBoardInfo.builder()
				.demandId(1)
				.demandState(DemandState.REGISTER)
				.itemId(1001)
				.itemQty(10)
				.itemPrice(500)
				.fee(5)
				.register(playerId)
				.updatedTime(DateTime.now())
				.createdTime(DateTime.now())
				.build();

		return DemandDto.builder()
				.demandBoardInfo(demandBoardInfo)
				.playerDemandInfo(playerDemandInfo)
				.build();
	}

	private static PlayerDemandInfo acquireDemand(final String playerId, final DemandBoardInfo boardInfo) {
		return PlayerDemandInfo.builder()
				.playerId(playerId)
				.playerDemandType(PlayerDemandType.FRIEND)
				.playerDemandState(PlayerDemandState.REGISTER)
				.demandId(boardInfo.getDemandId())
				.accepter(playerId)
				.updatedTime(DateTime.now())
				.createdTime(DateTime.now())
				.build();
	}

	private static void onCompleteAll(final DemandDto demandDto, final PlayerDemandInfo acquireDemandingInfo) {
		// 의뢰 게시판 상태 완료
		demandDto.getDemandBoardInfo().setDemandState(DemandState.COMPLETE);
		demandDto.getDemandBoardInfo().setUpdatedTime(DateTime.now());

		// 의뢰 수락자 완료
		acquireDemandingInfo.setUpdatedTime(DateTime.now());
		acquireDemandingInfo.setPlayerDemandState(PlayerDemandState.COMPLETE);

		// 의뢰 등록자 완료
		demandDto.getPlayerDemandInfo().setUpdatedTime(DateTime.now());
		demandDto.getPlayerDemandInfo().setPlayerDemandState(PlayerDemandState.COMPLETE);
	}
}