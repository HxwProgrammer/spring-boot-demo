package com.example.constant.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatStatusCode {
	OK(200),
	INTERNAL_SERVER_ERROR(500),
	;

	int value;
}
