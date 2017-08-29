package com.example.demo.repository.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionHolder {
	private static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

	public static List<WebSocketSession> getSessions() {
		return sessions;
	}

	public static WebSocketSession getSession(WebSocketSession session) {
		return sessions.stream()
				.filter(se -> se.equals(session))
				.findFirst().orElse(null);
	}

	public static void addSession(WebSocketSession session) {
		sessions.add(session);
	}

	public static void removeSession(WebSocketSession session) {
		sessions.remove(session);
	}
}
