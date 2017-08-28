package com.example.demo.message;

import com.example.constant.chat.ChatStatusCode;

import lombok.Data;

@Data
public class JarvisResponseEntity {
	private int statusCode;
	private long curTime;
	private Object body;

	public JarvisResponseEntity(final ChatStatusCode statusCode, final Object body) {
		this.statusCode = statusCode.getValue();
		this.curTime = System.currentTimeMillis();
		this.body = body;
	}
}
