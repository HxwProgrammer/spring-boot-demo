package com.example.demo02;

import java.io.IOException;
import java.util.Arrays;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.Functions;

public class SingleExample {
	public static void main(String[] args) {

		System.out.println("=======consumer");
		consumer();
		System.out.println("");

		System.out.println("=======biConsumer");
		biConsumer();
		System.out.println("");

		System.out.println("=======biConsumerError");
		biConsumerError();
		System.out.println("");

		System.out.println("=======successDisposed");
		successIsDisposed();
		System.out.println("");

		System.out.println("=======errorIsDisposed");
		errorIsDisposed();
		System.out.println("");

		System.out.println("=======biConsumerIsDisposedOnSuccess");
		biConsumerIsDisposedOnSuccess();
		System.out.println("");

		System.out.println("=======biConsumerIsDisposedOnError");
		biConsumerIsDisposedOnError();
		System.out.println("");
	}

	private static void consumer() {
		final Integer[] value = {null};
		Single.just(1).subscribe((v) -> value[0] = v);
		Arrays.stream(value).forEach(System.out::println);
	}

	private static void biConsumer() {
		final Object[] value = {null, null};
		Single.just(1).subscribe((v, e) -> {
			value[0] = v;
			value[1] = e;
		});
		Arrays.stream(value).forEach(System.out::println);
	}

	private static void biConsumerError() {
		final Object[] value = {null, null};
		Single.error(new RuntimeException()).subscribe((v, e) -> {
			value[0] = v;
			value[1] = e;
		});
		Arrays.stream(value).forEach(System.out::println);
	}

	private static void successIsDisposed() {
		final boolean result = Single.just(1).subscribe().isDisposed();
		System.out.println(result);
	}

	private static void errorIsDisposed() {
		final boolean result = Single.error(new RuntimeException()).subscribe(Functions.emptyConsumer(), Functions.emptyConsumer()).isDisposed();
		System.out.println(result);
	}

	private static void biConsumerIsDisposedOnSuccess() {
		final Object[] value = {null, null};
		Disposable disposable = Single.just(1).subscribe((v, e) -> {
			value[0] = v;
			value[1] = e;
		});
		System.out.println(disposable.isDisposed());
		Arrays.stream(value).forEach(System.out::println);
	}

	private static void biConsumerIsDisposedOnError() {
		final Object[] value = {null, null};
		Disposable disposable = Single.just(new IOException()).subscribe((v, e) -> {
			value[0] = v;
			value[1] = e;
		});
		System.out.println(disposable.isDisposed());
		Arrays.stream(value).forEach(System.out::println);
	}
}
