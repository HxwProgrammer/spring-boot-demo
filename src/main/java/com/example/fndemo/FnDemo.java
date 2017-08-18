package com.example.fndemo;

import java.util.Arrays;
import java.util.List;

public class FnDemo {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

		Integer firstElem = list.stream()
				.filter(i -> i > 5)
				.findFirst()
				.orElse(null);
		System.out.println("firstElem:" + firstElem);

		Integer findFirstElem = null;
		for (Integer i : list) {
			if (i > 5) {
				findFirstElem = i;
				break;
			}
		}

		System.out.println("firstElem:" + findFirstElem);

		//Supplier
		//Consumer



	}

}
