package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.message.JarvisResponseEntity;
import com.example.demo.repository.chat.WebSocketSessionHolder;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ChatService {
	@Autowired
	private Gson gson;

	public void broadcastMessage(final String userName, final JarvisResponseEntity entity) {
		WebSocketSessionHolder.getSessions().stream()
				.filter(session -> session.getAttributes().get("userName") != userName)
				.filter(Objects::nonNull)
				.filter(WebSocketSession::isOpen)
				.forEach(session -> {
					try {
						session.sendMessage(new TextMessage(gson.toJson(entity)));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}

	public List<String> getFilteredJoinerNames() {
		return WebSocketSessionHolder.getSessions().stream()
				.filter(Objects::nonNull)
				.filter(WebSocketSession::isOpen)
				.map(se -> se.getAttributes().get("userName"))
				.map(String::valueOf)
				.collect(toList());
	}

}
