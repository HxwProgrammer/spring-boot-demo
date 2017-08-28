package com.example.demo.message;

import com.example.constant.chat.ChatStatusCode;

import lombok.Data;

@Data
public class JarvisResponseEntity {
	private long curTime;
	private Object body;
	private int statusCode;

	public JarvisResponseEntity(final ChatStatusCode statusCode, final Object body) {
		this.curTime = System.currentTimeMillis();
		this.body = body;
		this.statusCode = statusCode.getValue();
	}
}
