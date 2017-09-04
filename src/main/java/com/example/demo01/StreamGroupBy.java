package com.example.demo01;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class StreamGroupBy {
	public static void main(String[] args) {
		List<String> items = Arrays.asList("apple", "banana", "orange", "apple");

		Map<String, Long> result = items.stream()
				.sorted()
				.collect(groupingBy(Function.identity(), counting()));

		System.out.println(result);
	}
}
