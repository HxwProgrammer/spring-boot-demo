package com.example.test;

import java.util.Arrays;
import java.util.stream.IntStream;

public class KakaoTest {
	public static void main(final String[] args) {
		// 1. 주어진 자연수를 모두 더하라.
		Solution s = new Solution();
		final int sum = s.solution(119);
		System.out.println(sum);

		// 2. 배열이 1부터 순차적으로 존재하면 true, 아니면 false
		final int[] input = {2, 1, 3, 4, 5, 1};
		boolean result = s.solution(input);
		System.out.println(result);
	}
}

class Solution {
	public int solution(final int n) {
		return Arrays
				.stream(String.valueOf(n).split(""))
				.mapToInt(Integer::parseInt)
				.sum();
	}

	public boolean solution(final int[] arr) {
		final int[] sortedArr = IntStream.of(arr).sorted().distinct().toArray();

		final int arrLength = arr.length;
		final int sortedArrLength = sortedArr.length;

		if (arrLength != sortedArrLength) {
			return false;
		}

		return (sortedArrLength == sortedArr[sortedArrLength - 1]);
	}
}