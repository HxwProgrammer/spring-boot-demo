package com.example.demo.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.example.constant.chat.MessageType;
import com.example.constant.chat.ChatStatusCode;
import com.example.demo.message.JarvisResponseEntity;
import com.example.demo.message.chat.BroadcastMessageRes;
import com.example.demo.message.chat.JoinRoomReq;
import com.example.demo.message.chat.JoinRoomRes;
import com.example.demo.message.chat.LeftRoomReq;
import com.example.demo.message.chat.LeftRoomRes;
import com.example.demo.message.chat.SendMessageReq;
import com.example.demo.message.chat.SendMessageRes;
import com.example.demo.mvc.annotation.JarvisCommand;
import com.example.demo.mvc.annotation.JarvisController;
import com.example.demo.repository.chat.WebSocketSessionHolder;
import com.example.demo.service.ChatService;

import lombok.extern.slf4j.Slf4j;

/**
 * packet example
 * url: ws://localhost:10001/api
 * <p>
 * joinRoom: {"command":"/chatCommand/joinRoom","params":{"userName":"1.player"}}
 * sendMessage: {"command":"/chatCommand/sendMessage","params":{"userName":"1.player", "message":"message send test"}}
 * leaveRoom: {"command":"/chatCommand/leftRoom","params":{"userName":"1.player"}}
 */
@Slf4j
@Component
@JarvisController
public class ChatCommand {

	@Autowired
	private ChatService chatService;

	@JarvisCommand(uri = "/joinRoom")
	public JoinRoomRes join(final WebSocketSession session, final JoinRoomReq req) {
		final JoinRoomRes res = new JoinRoomRes();

		final WebSocketSession s = WebSocketSessionHolder.getSession(session);
		if (s == null) {
			session.getAttributes().putIfAbsent("userName", req.getUserName());
			WebSocketSessionHolder.addSession(session);
		}

		List<String> joinerNames = chatService.getFilteredJoinerNames();

		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setMessageType(MessageType.B_USER_JOINED.name());
		broadcastMessageRes.setUserName(req.getUserName());
		broadcastMessageRes.setMessage(req.getUserName() + " has joined.");
		broadcastMessageRes.setJoinerNames(joinerNames);

		chatService.broadcastMessage(req.getUserName(), new JarvisResponseEntity(ChatStatusCode.OK, broadcastMessageRes));

		res.setJoinerNames(joinerNames);
		res.setMessageType(MessageType.S_USER_JOINED.name());

		return res;
	}

	@JarvisCommand(uri = "/leftRoom")
	public LeftRoomRes leave(final WebSocketSession session, final LeftRoomReq req) {
		final LeftRoomRes res = new LeftRoomRes();

		WebSocketSessionHolder.removeSession(session);

		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setMessageType(MessageType.B_USER_LEFT.name());
		broadcastMessageRes.setUserName(req.getUserName());
		broadcastMessageRes.setMessage(req.getUserName() + " has left.");
		broadcastMessageRes.setJoinerNames(chatService.getFilteredJoinerNames());

		chatService.broadcastMessage(req.getUserName(), new JarvisResponseEntity(ChatStatusCode.OK, broadcastMessageRes));

		return res;
	}

	@JarvisCommand(uri = "/sendMessage")
	public SendMessageRes sendMessage(final WebSocketSession session, final SendMessageReq req) {
		final SendMessageRes res = new SendMessageRes();

		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setMessageType(MessageType.B_SEND_MESSAGE.name());
		broadcastMessageRes.setUserName(req.getUserName());
		broadcastMessageRes.setMessage(req.getMessage());

		chatService.broadcastMessage(req.getUserName(), new JarvisResponseEntity(ChatStatusCode.OK, broadcastMessageRes));

		res.setMessage(req.getMessage());
		return res;
	}
}
