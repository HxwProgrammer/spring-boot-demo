package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.constant.chat.ChatStatusCode;
import com.example.demo.message.JarvisResponseEntity;
import com.example.demo.message.chat.BroadcastMessageRes;
import com.example.demo.repository.chat.WebSocketSessionHolder;
import com.example.demo.util.ChatUtil;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ChatService {
	@Autowired
	private Gson gson;

	public void broadcastMessage(final WebSocketSession session, final JarvisResponseEntity entity) {
		WebSocketSessionHolder.getSessions().stream()
				.filter(se -> !se.equals(session))
				.filter(Objects::nonNull)
				.filter(WebSocketSession::isOpen)
				.forEach(se -> {
					try {
						se.sendMessage(new TextMessage(gson.toJson(entity)));
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

	public void disconnected(final WebSocketSession session) {
		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setMessage(ChatUtil.getUserNameViaSession(session) + " has disconnected.");
		broadcastMessageRes.setJoinerNames(getFilteredJoinerNames());
		broadcastMessage(session, new JarvisResponseEntity("/disconnected", ChatStatusCode.OK, broadcastMessageRes));
	}

}
