package com.example.demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import io.reactivex.Observable;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * 게시글 중 7일 이내 글인 경우 isNew 를 활성화 해주기
 */
@Slf4j
public class StreamBoardExample {
	public static void main(String[] args) {

		// 방명록 목록
		final List<Bbs> result = Arrays.asList(
				new Bbs(1, false, DateTime.now().minusDays(10)),
				new Bbs(2, false, DateTime.now().minusDays(9)),
				new Bbs(3, false, DateTime.now().minusDays(3)),
				new Bbs(4, false, DateTime.now().minusDays(2))
		);

		print("Before", result);

		// 1. Imperative
		List<Bbs> result1 = byImperative(new ArrayList<>(result));
		print("After Imperative", result1);

		// 2. Stream
		List<Bbs> result2 = byStream(new ArrayList<>(result));
		print("After Stream", result2);

		// 3. RxJava Observable
		byRxObservable("After RxJava Observable", new ArrayList<>(result));
	}

	private static boolean isWithIn7Days(@NonNull Bbs bbs) {
		return (bbs.getRegDateTime().isAfter(DateTime.now().minusDays(7).toInstant()));
	}

	private static void doIfTrue(boolean isTrue, Runnable runnable) {
		if (isTrue) {
			runnable.run();
		}
	}

	private static void print(final String tag, final List<Bbs> result) {
		System.out.println(tag);
		System.out.println("================================================");
		System.out.println(result.stream().map(String::valueOf).collect(joining("\n")));
		System.out.println("================================================");
	}

	private static List<Bbs> byImperative(final List<Bbs> result) {
		for (final Bbs bbs : result) {
			if (isWithIn7Days(bbs)) {
				bbs.setNew(true);
			}
		}

		return result;
	}

	private static List<Bbs> byStream(final List<Bbs> result) {
		return result.stream()
				.map(bbs -> bbs.setFieldIfTrue(StreamBoardExample::isWithIn7Days, b -> b.setNew(true)))
				//.peek(bbs -> doIfTrue(isWithIn7Days(bbs), () -> bbs.setNew(true)))
				.collect(toList());
	}

	private static void byRxObservable(final String tag, final List<Bbs> result) {
		System.out.println(tag);
		System.out.println("================================================");
		Observable.fromIterable(result)
				.map(bbs -> bbs.setFieldIfTrue(StreamBoardExample::isWithIn7Days, b -> b.setNew(true)))
				.subscribe(System.out::println);
	}

}

@AllArgsConstructor
@Data
class Bbs {
	private int seq;
	private boolean isNew;
	private DateTime regDateTime;

	public Bbs setFieldIfTrue(Predicate<Bbs> predicate, Consumer<Bbs> setter) {
		if (predicate.test(this)) {
			setter.accept(this);
		}
		return this;
	}
}