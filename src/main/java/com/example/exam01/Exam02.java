package com.example.exam01;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class Exam02 {
	public static void main(String[] args) {
		final List<Coin> coinList = Arrays.asList(
			new Coin("Yen", new BigDecimal(500)),
			new Coin("Won", new BigDecimal(20000)),
			new Coin("Euro", new BigDecimal(1111)),
			new Coin("Won", new BigDecimal(5000)),
			new Coin("Dollar", new BigDecimal(638)),
			new Coin("Dollar", new BigDecimal(75)),
			new Coin("Yen", new BigDecimal(200)),
			new Coin("Euro", new BigDecimal(800)),
			new Coin("Yen", new BigDecimal(3200)),
			new Coin("Won", new BigDecimal(300)),
			new Coin("Dollar", new BigDecimal(100)),
			new Coin("Euro", new BigDecimal(999))
		);



		// 화폐별로 그루핑
		final Map<String, List<Coin>> coinMap = new HashMap<>();
		for (final Coin coin : coinList) {
			final List<Coin> coins;
			if (coinMap.containsKey(coin.getCurrencyName())) {
				coins = coinMap.get(coin.getCurrencyName());
			} else {
				coins = new ArrayList<>();
			}
			coins.add(coin);
			coinMap.put(coin.getCurrencyName(), coins);
		}

		// 화폐별로 합계
		final StringBuilder stringBuilder = new StringBuilder();
		for (Entry<String, List<Coin>> entry : coinMap.entrySet()) {
			BigDecimal amount = BigDecimal.ZERO;
			for (Coin c : entry.getValue()) {
				amount = amount.add(c.getAmount());
			}
			stringBuilder.append(entry.getKey()).append("=").append(amount). append("\n");
		}
		System.out.println(stringBuilder.toString());

		// 화폐별로 합계 (Stream)
		System.out.println(
			coinList.stream()
				.collect(groupingBy(Coin::getCurrencyName))
				.entrySet()
				.stream()
				.map(entry -> entry.getKey() + "=" + entry.getValue().stream()
					.map(Coin::getAmount)
					.reduce(BigDecimal.ZERO, BigDecimal::add))
				.collect(joining("\n"))
		);
	}
}

@Data
class Coin {
	private final String currencyName;
	private final BigDecimal amount;
}