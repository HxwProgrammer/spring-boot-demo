package com.example.demo.message.hello;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloReq {
	private int id;
	private String name;
}
