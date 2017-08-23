package com.example.demo02;

import io.reactivex.Observable;

public class RxExample01 {
	public static void main(String[] args) {
		// 구구단을 Observable 로 print 하기
		observableMultiplicationTablePrint(1);
		observableMultiplicationTablePrint(2);
		observableMultiplicationTablePrint(3);
	}

	private static void observableMultiplicationTablePrint(final int step) {
		Observable.range(1, 9)
				.map(value -> draw(step, value))
				.subscribe(System.out::println);

		System.out.println("===========");
	}

	private static String draw(final int step, final int value) {
		return step + " * " + value + " = " + step * value;
	}
}
