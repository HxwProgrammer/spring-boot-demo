package com.example.demo.message.chat;

import java.util.List;

import lombok.Data;

@Data
public class BroadcastMessageRes {
	private String userName;
	private String message;
	private List<String> joinerNames;
}
