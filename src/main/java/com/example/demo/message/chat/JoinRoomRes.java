package com.example.demo.message.chat;

import java.util.List;

import lombok.Data;

@Data
public class JoinRoomRes {
	private String userName;
	private String message;
	private List<String> joinerNames;
}
