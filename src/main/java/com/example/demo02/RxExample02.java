package com.example.demo02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import rx.Observable;

@Slf4j
public class RxExample02 {
	public static void main(String[] args) {
		// 누산기
		Observable
				.range(1, 100)
				.scan((total, chunk) -> total + chunk)
				.last()
				.subscribe(v -> log.info("i:{}", v));

		Observable
				.range(1, 100)
				.map(i -> i)
				.reduce(0, (total, chunk) -> total + chunk)
				.subscribe(i -> log.info("i:{}", i));

		Observable
				.range(10, 20)
				.collect(ArrayList::new, List::add)
				.subscribe(list -> log.info("list:{}", list));


		Observable<Integer> randomInts = Observable.create(subscriber -> {
			Random random = new Random();
			while (!subscriber.isUnsubscribed()) {
				subscriber.onNext(random.nextInt(1000));
			}
		});
		randomInts
				.distinct()
				.take(10)
				.subscribe(value -> log.info("randomInt:{}", value));


		// 1. 추천 조회시 오류
		// 2. 오류 대응으로 베스트 셀러 조회
		BookService bookService = new BookService();
		Observable<Book> recommended = bookService.recommend();
		Observable<Book> bestSeller = bookService.bestSeller();
		Observable<Book> book = recommended.onErrorResumeNext(bestSeller);
		book.subscribe(System.out::println);
	}
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class Book {
	int bookId;
	String bookName;
}

class BookService {
	public Observable<Book> recommend() {
		//Book book = new Book(1, "추천");
		return Observable.error(new Exception("추천 오류 발생"));
	}

	public Observable<Book> bestSeller() {
		Book book = new Book(2, "베스트셀러");
		return Observable.just(book);
	}
}