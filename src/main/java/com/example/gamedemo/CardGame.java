package com.example.gamedemo;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CardGame {
	public static void main(String[] args) {
		// 트리플 트라이어드

		// p1 카드
		final Card[] p1Cards = {
			createCard(7, 2, 4, 1, 1),
			createCard(2, 6, 5, 3, 1),
			createCard(3, 3, 6, 2, 1),
			createCard(4, 2, 7, 7, 1),
			createCard(5, 1, 9, 9, 1),
		};

		// p2 카드
		final Card[] p2Cards = {
			createCard(5, 1, 8, 9, 2),
			createCard(6, 3, 1, 4, 2),
			createCard(3, 3, 6, 2, 2),
			createCard(2, 6, 5, 3, 2),
			createCard(4, 2, 7, 7, 2),
		};

		final Board[] boards = new Board[9];

		boards[0] = new Board(0, 1, p1Cards[0]);
		checkWin(0, 1, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[1] = new Board(1, 2, p2Cards[0]);
		checkWin(1, 2, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[3] = new Board(3, 1, p1Cards[1]);
		checkWin(3, 1, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[8] = new Board(8, 2, p2Cards[1]);
		checkWin(8, 2, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[4] = new Board(4, 1, p1Cards[2]);
		checkWin(4, 1, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[6] = new Board(6, 2, p2Cards[2]);
		checkWin(6, 2, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[7] = new Board(7, 1, p1Cards[3]);
		checkWin(7, 1, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[2] = new Board(2, 2, p2Cards[3]);
		checkWin(2, 2, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		boards[5] = new Board(5, 1, p1Cards[4]);
		checkWin(5, 1, boards);

		System.out.println("----------------------");
		Arrays.stream(boards).forEach(System.out::println);
		System.out.println("----------------------");

		final int count1 = (int) Arrays.stream(boards)
			.filter(v -> v.getPlayer() == 1)
			.count();

		final int count2 = (int) Arrays.stream(boards)
			.filter(v -> v.getPlayer() == 2)
			.count();

		System.out.println("----------------------");
		System.out.println("player1 card count:" + count1);
		System.out.println("player2 card count:" + count2);
	}

	private static void checkWin(final int key, final int player, final Board[] boards) {
		// 왼쪽 (-1) 오른쪽(+1) 위 (-4) 아래 (+3)

		final Board current = boards[key];

		// 왼쪽
		if (key - 1 >= 0 && boards[key - 1] != null) {
			if (boards[key - 1].getCard().getRight() < current.getCard().getLeft()) {
				boards[key - 1].setPlayer(player);
			}
		}

		// 오른쪽
		if (key + 1 < 9 && boards[key + 1] != null) {
			if (boards[key + 1].getCard().getLeft() < current.getCard().getRight()) {
				boards[key + 1].setPlayer(player);
			}
		}

		// 위
		if (key - 4 >= 0 && boards[key - 4] != null) {
			if (boards[key - 4].getCard().getDown() < current.getCard().getUp()) {
				boards[key - 4].setPlayer(player);
			}
		}

		// 아래
		if (key + 3 < 9 && boards[key + 3] != null) {
			if (boards[key + 3].getCard().getUp() < current.getCard().getDown()) {
				boards[key + 3].setPlayer(player);
			}
		}
	}

	private static Card createCard(final int up, final int down, final int left, final int right, final int player) {
		return new Card(up, down, left, right, player);
	}
}

@AllArgsConstructor
@Data
final class Card {
	private int up;
	private int down;
	private int left;
	private int right;
	private int player;
}

@AllArgsConstructor
@Data
class Board {
	private int slotNo;
	private int player;
	private Card card;
}
