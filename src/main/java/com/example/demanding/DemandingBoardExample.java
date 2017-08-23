package com.example.demanding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.joda.time.DateTime;

import com.example.demanding.DemandingBoardInfo.DemandingState;
import com.example.demanding.PlayerDemandingInfo.PlayerDemandingState;
import com.example.demanding.PlayerDemandingInfo.PlayerDemandingType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemandingBoardExample {
	public static void main(String[] args) {
		final AtomicInteger demandingId = new AtomicInteger(1);

		final List<DemandingBoardInfo> demandingBoardInfoList = new ArrayList<>();

		// 1. 1.player 의뢰 게시판에 등록
		// 2. 2.player 가 1.player 의 의뢰 입수
		// 3. 2.player 가 아이템 납품
		// 4. 1.player 가 아이템 획득 및 수수료 지불

		final int firstId = demandingId.getAndAdd(1);

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
		final DemandingDto demandingDto = addDemanding(firstPlayerId, firstId);
		demandingBoardInfoList.add(demandingDto.getDemandingBoardInfo());

		demandingDto.getDemandingBoardInfo().setAccepter(secondPlayerId);
		demandingDto.getDemandingBoardInfo().setUpdatedTime(DateTime.now());

		// 2.player 가 1.player 의 의뢰 입수
		final PlayerDemandingInfo acquireDemandingInfo = acquireDemanding(secondPlayerId, demandingDto.getDemandingBoardInfo());

		// 2.player 가 아이템 납품 및 골드 입수
		secondPlayerInventory.get(demandingDto.getDemandingBoardInfo().getItemId()).decrease(demandingDto.getDemandingBoardInfo().getItemQty());
		secondPlayerInfo.increaseGold(demandingDto.getDemandingBoardInfo().getItemPrice() * demandingDto.getDemandingBoardInfo().getItemQty() - demandingDto.getDemandingBoardInfo().getFee());

		// 4. 1.player 아이템 획득, 금액 및 수수료 지불
		firstPlayerInventory.get(demandingDto.getDemandingBoardInfo().getItemId()).increase(demandingDto.getDemandingBoardInfo().getItemQty());
		firstPlayerInfo.decreaseGold(demandingDto.getDemandingBoardInfo().getItemPrice() * demandingDto.getDemandingBoardInfo().getItemQty() + demandingDto.getDemandingBoardInfo().getFee());

		onCompleteAll(demandingDto, acquireDemandingInfo);

		log.info("amount:{}", demandingDto.getDemandingBoardInfo().getFee() * demandingDto.getDemandingBoardInfo().getItemQty());

		log.info("after firstPlayerInfo:{}", firstPlayerInfo);
		log.info("after firstPlayerInventory:{}", firstPlayerInventory);
		log.info("after secondPlayerInfo:{}", secondPlayerInfo);
		log.info("after secondPlayerInventory:{}", secondPlayerInventory);

		log.info("demandingBoardInfoList:{}", demandingBoardInfoList);
		log.info("firstPlayer PlayerDemandingInfo:{}", demandingDto.getPlayerDemandingInfo());
		log.info("secondPlayer acquireDemandingInfo:{}", acquireDemandingInfo);
	}

	private static DemandingDto addDemanding(final String playerId, final int demandingId) {
		final PlayerDemandingInfo playerDemandingInfo = PlayerDemandingInfo.builder()
				.playerId(playerId)
				.playerDemandingType(PlayerDemandingType.MY)
				.playerDemandingState(PlayerDemandingState.REGISTER)
				.demandingId(demandingId)
				.accepter(playerId)
				.updatedTime(DateTime.now())
				.createdTime(DateTime.now())
				.build();

		final DemandingBoardInfo demandingBoardInfo = DemandingBoardInfo.builder()
				.demandingId(1)
				.demandingState(DemandingState.REGISTER)
				.itemId(1001)
				.itemQty(10)
				.itemPrice(500)
				.fee(5)
				.register(playerId)
				.updatedTime(DateTime.now())
				.createdTime(DateTime.now())
				.build();

		return DemandingDto.builder()
				.demandingBoardInfo(demandingBoardInfo)
				.playerDemandingInfo(playerDemandingInfo)
				.build();
	}

	private static PlayerDemandingInfo acquireDemanding(final String playerId, final DemandingBoardInfo boardInfo) {
		return PlayerDemandingInfo.builder()
				.playerId(playerId)
				.playerDemandingType(PlayerDemandingType.FRIEND)
				.playerDemandingState(PlayerDemandingState.REGISTER)
				.demandingId(boardInfo.getDemandingId())
				.accepter(playerId)
				.updatedTime(DateTime.now())
				.createdTime(DateTime.now())
				.build();
	}

	private static void onCompleteAll(final DemandingDto demandingDto, final PlayerDemandingInfo acquireDemandingInfo) {
		// 의뢰 게시판 상태 완료
		demandingDto.getDemandingBoardInfo().setDemandingState(DemandingState.COMPLETE);
		demandingDto.getDemandingBoardInfo().setUpdatedTime(DateTime.now());

		// 의뢰 수락자 완료
		acquireDemandingInfo.setUpdatedTime(DateTime.now());
		acquireDemandingInfo.setPlayerDemandingState(PlayerDemandingState.COMPLETE);

		// 의뢰 등록자 완료
		demandingDto.getPlayerDemandingInfo().setUpdatedTime(DateTime.now());
		demandingDto.getPlayerDemandingInfo().setPlayerDemandingState(PlayerDemandingState.COMPLETE);
	}
}