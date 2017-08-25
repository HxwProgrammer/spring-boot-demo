package com.example.demo.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JarvisWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private JarvisMessageHandler jarvisMessageHandler;

	@Override
	protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
		log.info("session:{}, message:{}", session, message);
		jarvisMessageHandler.execute(session, message.getPayload());
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
		log.info("afterConnectionEstablished:{}", session);
	}

	@Override
	public void handleTransportError(final WebSocketSession session, final Throwable exception) throws Exception {
		log.info("handleTransportError:{}, e:{}", session, exception.getMessage());
	}

	@Override
	public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
		log.info("afterConnectionClosed:{}, status:{}", session, status);
	}
}
