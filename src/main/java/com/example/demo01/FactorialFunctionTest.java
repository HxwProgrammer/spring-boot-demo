package com.example.demo01;

import java.util.stream.IntStream;

public class FactorialFunctionTest {
	public static void main(String[] args) {
		System.out.println("factorialByForLoop:" + factorialByForLoop(5));

		System.out.println("factorialByStream:" + factorialByStream(5));

		System.out.println("factorialByRecursiveFunction:" + factorialByRecursiveFunction(5));
	}

	public static int factorialByForLoop(final int number) {
		int result = 1;
		for (int i = 1; i <= number; i++) {
			result *= i;
		}

		return result;
	}

	public static int factorialByStream(final int number) {
		final int[] result = {1};
		IntStream.range(1, number + 1).forEach(n -> result[0] *= n);
		return result[0];
	}

	public static int factorialByRecursiveFunction(final int number) {
		if (number == 1) {
			return 1;
		}
		return number * factorialByRecursiveFunction(number - 1);
	}
}
