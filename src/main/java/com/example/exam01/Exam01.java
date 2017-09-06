package com.example.exam01;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Exam01 {
	public static void main(String[] args) {
		// primitive types
		final short shortValue = 1;
		final int intValue = 1;
		final long longValue = 1_000_000_000_000L;

		final double doubleValue = 1_000_000_000_000d;
		final float floatValue = 1f;

		final boolean booleanValue = true;

		final char charValue = 'A';

		final byte byteValue = 100;


		// java.lang
		final Short shortValue2 = null;
		final Integer intValue2 = null;
		final Long longValue2 = null;

		final Double doubleValue2 = null;
		final Float floatValue2 = null;

		final Boolean booleanValue2 = null;

		final Character charValue2 = null;

		final Byte byteValue2 = null;

		final List<Integer> iList = new ArrayList<>();

		final BigInteger bigInteger = null;

		final BigDecimal bigDecimal = BigDecimal.ONE;
		BigDecimal result = bigDecimal.add(BigDecimal.valueOf(10_000_000_000_000L));
		System.out.println(result);

		// map
		Map<String, Object> map = new HashMap<>();
		map.put("e1", 1);
		map.put("e2", 2);
		map.put("e3", 3);

		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println("key:" + entry.getKey());
			System.out.println("value:" + entry.getValue());
		}

		// collection
		List<String> list = new ArrayList<>();
		list.add("e1");
		list.add("e3");
		list.add("e2");
		for (String elem : list) {
			System.out.println("list elem:" + elem);
		}

		Collection<String> collection = new ArrayList<>();
		collection.add("e1");
		collection.add("e2");
		collection.add("e3");
		for (String elem : collection) {
			System.out.println("collection elem:" + elem);
		}

		Iterator<String> iterator = collection.iterator();
		while (iterator.hasNext()) {
			System.out.println("iterable elem:" + iterator.next());
		}

		Set<String> set = new HashSet<>();
		set.add("e1");
		set.add("e2");
		set.add("e3");
		set.add("e3");
		for (String elem : set) {
			System.out.println("set elem:" + elem);
		}

		// stream
		List<Integer> listSample = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> resultByStream = listSample.stream()
				.filter(i -> {
					System.out.println("i : " + i);
					return i > 3;
				})
				.map(i -> i * 3)
				.collect(toList());
		System.out.println("stream result : " + resultByStream);

		// imperative
		List<Integer> resultByImperative = new ArrayList<>();
		for (Integer i : listSample) {
			if (i > 3) {
				resultByImperative.add(i);
			}
		}
		System.out.println("imperative result : " + resultByImperative);

	}
}
