package com.example.demo.message;

import com.example.demo.constant.chat.ChatStatusCode;

import lombok.Data;

@Data
public class JarvisResponseEntity {
	private String command;
	private long reqId;
	private int statusCode;
	private long curTime;
	private Object body;

	public JarvisResponseEntity(final String command, final ChatStatusCode statusCode, final Object body) {
		this.command = command;
		this.statusCode = statusCode.getValue();
		this.curTime = System.currentTimeMillis();
		this.body = body;
	}
}
