package com.example.demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class StreamExample {
	public static void main(String[] args) {
		System.out.println("byImperative: " + byImperative());

		System.out.println("byStream: " + byStream());

		System.out.println("byCustomFunction: " + byCustomFunction());
	}

	private static int byImperative() {
		final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		int result = -1;

		for (final Integer number : numbers) {
			if (number > 3 && number < 9) {
				final Integer newNumber = number * 2;
				if (newNumber > 10) {
					result = newNumber;
					break;
				}
			}
		}

		return result;
	}

	private static int byStream() {

		AtomicInteger count = new AtomicInteger(1);

		return IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
				.filter(number -> {
					System.out.println(count.getAndAdd(1) + ": number > 3");
					return number > 3;
				})
				.filter(number -> {
					System.out.println(count.getAndAdd(1) + ": number < 9");
					return number < 9;
				})
				.map(number -> {
					System.out.println(count.getAndAdd(1) + ": number * 2");
					return number * 2;
				})
				.filter(number -> {
					System.out.println(count.getAndAdd(1) + ": number > 20");
					return number > 10;
				})
				.findFirst()
				.orElse(-1);
	}

	private static int byCustomFunction() {
		final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		AtomicInteger count = new AtomicInteger(1);

		final List<Integer> greaterThan3 = filter(numbers, i -> {
			System.out.println(count.getAndAdd(1) + ": i > 3");
			return i > 3;
		});

		final List<Integer> lessThan9 = filter(greaterThan3, i ->  {
			System.out.println(count.getAndAdd(1) + ": i < 9");
			return i < 9;
		});

		final List<Integer> doubled = map(lessThan9, i -> {
			System.out.println(count.getAndAdd(1) + ": i * 2");
			return i * 2;
		});

		final List<Integer> greaterThan10 = filter(doubled, i -> {
			System.out.println(count.getAndAdd(1) + ": i > 10");
			return i > 10;
		});

		Integer result = greaterThan10.get(0);

		return result == null ? -1 : result;
	}


	private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
		final List<T> result = new ArrayList<>();
		for (final T l : list) {
			if (predicate.test(l)) {
				result.add(l);
			}
		}
		return result;
	}

	private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
		final List<R> result = new ArrayList<>();
		for (final T l : list) {
			result.add(mapper.apply(l));
		}
		return result;
	}
}
