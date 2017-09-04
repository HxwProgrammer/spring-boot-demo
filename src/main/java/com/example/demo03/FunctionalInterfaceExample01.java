package com.example.demo03;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaceExample01 {
	public static void main(String[] args) {

		// parameter empty, return T type
		Supplier<String> supplier = () -> " supplier";
		System.out.println("Hello" + supplier.get());

		// parameter T type, return void
		Consumer<String> consumer = arg -> System.out.println("Hello " + arg);
		consumer.accept("consumer");

		// parameter T type, return boolean
		Predicate<String> predicate = arg -> arg.contains("H");
		System.out.println("Hello : " + predicate.test("Hello"));
		System.out.println("ello : " + predicate.test("ello"));

		// parameter T type, return R type
		Function<String, Consumer<String>> fn = fArg -> cArg -> System.out.println(fArg + cArg);
		fn.apply("Hello ").accept("Java");
	}
}

