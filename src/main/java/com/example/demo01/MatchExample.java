package com.example.demo01;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchExample {
	public static void main(String[] args) {

		// 매칭 대상자 목록
		final Map<String, PlayerInfo> receptionList = new ConcurrentHashMap<>();

		PlayerInfo p1 = new PlayerInfo("p1", 1, 1000, 0);
		PlayerInfo p2 = new PlayerInfo("p2", 1, 1050, 0);
		PlayerInfo p3 = new PlayerInfo("p3", 1, 950, 0);
		PlayerInfo p4 = new PlayerInfo("p4", 1, 1000, 0);
		PlayerInfo p5 = new PlayerInfo("p5", 1, 1500, 0);
		PlayerInfo p6 = new PlayerInfo("p6", 1, 1200, 0);
		PlayerInfo p7 = new PlayerInfo("p7", 1, 1100, 0);
		PlayerInfo p8 = new PlayerInfo("p8", 1, 1030, 0);
		PlayerInfo p9 = new PlayerInfo("p9", 1, 1020, 0);
		PlayerInfo p10 = new PlayerInfo("p10", 1, 1010, 0);

		receptionList.put("p1", p1);
		receptionList.put("p2", p2);
		receptionList.put("p3", p3);
		receptionList.put("p4", p4);
		receptionList.put("p5", p5);
		receptionList.put("p6", p6);
		receptionList.put("p7", p7);
		receptionList.put("p8", p8);
		receptionList.put("p9", p9);
		receptionList.put("p10", p10);

		PlayerInfo targetPlayer = findTargetPlayer(receptionList, p1, 50, 5);
		log.info("targetPlayer(p1) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p1, 50, 5);
		log.info("targetPlayer(p1) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p2, 50, 5);
		log.info("targetPlayer(p2) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p3, 50, 5);
		log.info("targetPlayer(p3) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p4, 50, 5);
		log.info("targetPlayer(p4) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p5, 50, 5);
		log.info("targetPlayer(p5) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p6, 50, 5);
		log.info("targetPlayer(p6) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p7, 50, 5);
		log.info("targetPlayer(p7) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p8, 50, 5);
		log.info("targetPlayer(p8) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p9, 50, 5);
		log.info("targetPlayer(p9) : {}", targetPlayer);

		targetPlayer = findTargetPlayer(receptionList, p10, 50, 5);
		log.info("targetPlayer(p10) : {}", targetPlayer);

		receptionList.values().forEach(info -> log.info("receptionList : {}", info));
	}

	private static PlayerInfo findTargetPlayer(final Map<String, PlayerInfo> receptionList, final PlayerInfo p, int searchPoint, final int searchAddPoint) {
		if (p.getState() != 0) {
			return null;
		}

		PlayerInfo targetPlayer = null;
		int tryCount = 5;
		while (tryCount > 0) {
			final int point = searchPoint;
			List<PlayerInfo> selectedList = receptionList.values().stream()
					.filter(info -> info.getState() == 0)
					.filter(info -> !p.getPlayerId().equals(info.getPlayerId()))
					.filter(info -> info.getPoint() >= p.getPoint() - point)
					.filter(info -> info.getPoint() <= p.getPoint() + point)
					.collect(Collectors.toList());

			if (selectedList.size() > 0) {
				targetPlayer = selectedList.get(new Random().nextInt(selectedList.size()));
				break;
			}
			tryCount--;
			searchPoint += searchAddPoint;
		}

		if (targetPlayer != null) {
			receptionList.get(p.getPlayerId()).setState(1);
			receptionList.get(targetPlayer.getPlayerId()).setState(1);
		}

		return targetPlayer;
	}

	@AllArgsConstructor
	@Data
	private static class PlayerInfo {
		private String playerId;
		private int grade;
		private int point;
		private int state; // 0 : 매칭 전, 1 : 매칭
	}

}
