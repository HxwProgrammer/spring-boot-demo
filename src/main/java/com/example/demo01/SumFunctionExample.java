package com.example.demo01;

import java.util.stream.IntStream;

public class SumFunctionExample {
	public static void main(String[] args) {
		System.out.println("sum1ToNumberByForLoop(100):" + sum1ToNumberByForLoop(100));
		System.out.println("sum1ToNumberByForLoop(99):" + sum1ToNumberByForLoop(99));

		System.out.println("sum1ToNumberByStream(100):" + sum1ToNumberByStream(100));
		System.out.println("sum1ToNumberByStream(99):" + sum1ToNumberByStream(99));

		System.out.println("sum1ToNumberByRecursiveFunction(100):" + sum1ToNumberByRecursiveFunction(100));
		System.out.println("sum1ToNumberByRecursiveFunction(99):" + sum1ToNumberByRecursiveFunction(99));
	}

	private static int sum1ToNumberByForLoop(final int number) {
		int sum = 0;

		for (int i = 1; i <= number; i++) {
			sum += i;
		}

		return sum;
	}

	private static int sum1ToNumberByStream(final int number) {
		return IntStream.rangeClosed(1, number).sum();
	}

	private static int sum1ToNumberByRecursiveFunction(final int number) {
		//  ___________________________
		// |                           |
		// 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
		if (number % 2 == 0) {
			return (1 + number) * (number / 2);
		} else {
			return sum1ToNumberByRecursiveFunction(number - 1) + number;
		}
	}

}
