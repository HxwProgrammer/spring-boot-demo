package com.example.demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class StreamFlatMapExample01 {
	public static void main(String[] args) {
		final String[][] data = {{"a", "b"}, {"c", "d"}};

		List<String> result = Arrays.stream(data)
				.flatMap(x -> {
					System.out.println("x:" + Arrays.toString(x));
					return Arrays.stream(x);
				})
				.collect(toList());
		System.out.println("result : " + result);


		List<String> result2 = new ArrayList<>();
		for (String[] dArray : data) {
			result2.addAll(Arrays.asList(dArray));
		}
		System.out.println("result2 : " + result2);
	}
}
