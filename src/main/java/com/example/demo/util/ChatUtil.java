package com.example.demo.util;

import org.springframework.web.socket.WebSocketSession;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUtil {
	public static String getUserNameViaSession(final WebSocketSession session) {
		String userName = (String) session.getAttributes().get("userName");
		return (userName == null) ? "unknown" : userName;
	}
}
