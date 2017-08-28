package com.example.demo.message.chat;

import java.util.List;

import lombok.Data;

@Data
public class JoinRoomRes {
	private String messageType;
	private List<String> joinerNames;
}
