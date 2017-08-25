package com.example.demo.message.hello;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HelloRes {
	private HelloReq request;
}
