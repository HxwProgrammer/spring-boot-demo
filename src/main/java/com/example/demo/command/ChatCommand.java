package com.example.demo.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

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
		final WebSocketSession s = WebSocketSessionHolder.getSession(session);
		if (s == null) {
			session.getAttributes().putIfAbsent("userName", req.getUserName());
			WebSocketSessionHolder.addSession(session);
		}

		List<String> joinerNames = chatService.getFilteredJoinerNames();

		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setMessage(req.getUserName() + " has joined.");
		broadcastMessageRes.setJoinerNames(joinerNames);

		chatService.broadcastMessage(session, new JarvisResponseEntity("/chatCommand/joinRoom", ChatStatusCode.OK, broadcastMessageRes));

		final JoinRoomRes res = new JoinRoomRes();
		res.setMessage(req.getUserName() + " has joined.");
		res.setJoinerNames(joinerNames);

		return res;
	}

	@JarvisCommand(uri = "/leftRoom")
	public LeftRoomRes left(final WebSocketSession session, final LeftRoomReq req) {
		WebSocketSessionHolder.removeSession(session);

		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setMessage(req.getUserName() + " has left.");
		broadcastMessageRes.setJoinerNames(chatService.getFilteredJoinerNames());

		chatService.broadcastMessage(session, new JarvisResponseEntity("/chatCommand/leftRoom", ChatStatusCode.OK, broadcastMessageRes));

		return new LeftRoomRes();
	}

	@JarvisCommand(uri = "/sendMessage")
	public SendMessageRes sendMessage(final WebSocketSession session, final SendMessageReq req) {
		final BroadcastMessageRes broadcastMessageRes = new BroadcastMessageRes();
		broadcastMessageRes.setUserName(req.getUserName());
		broadcastMessageRes.setMessage(req.getMessage());

		chatService.broadcastMessage(session, new JarvisResponseEntity("/chatCommand/sendMessage", ChatStatusCode.OK, broadcastMessageRes));

		final SendMessageRes res = new SendMessageRes();
		res.setUserName(req.getUserName());
		res.setMessage(req.getMessage());

		return res;
	}
}
