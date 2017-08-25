package com.example.demo.message;

import lombok.Data;

@Data
public class JarvisResponseEntity {
	private long curTime;
	private Object body;
	private int stateCode;

	public JarvisResponseEntity(final int stateCode, final Object body) {
		this.curTime = System.currentTimeMillis();
		this.body = body;
		this.stateCode = stateCode;
	}
}
