package com.example.demo03;

import java.sql.Timestamp;

import com.github.davidmoten.rx.jdbc.Database;

public class DatabaseExample {
	public static void main(String[] args) {
		select1();

		selectNow();
	}

	private static Database db() {
		return Database.builder()
				.url("jdbc:h2:~/test")
				.username("")
				.password("")
				.build();
	}

	private static void select1() {
		Long result = db().select("select 1")
				.getAs(Long.class)
				.toBlocking()
				.single();
		System.out.println(result);
	}

	private static void selectNow() {
		Timestamp result = db().select("select now()")
				.getAs(Timestamp.class)
				.toBlocking()
				.single();
		System.out.println(result);
	}
}
