package com.example.demo.entity;

public class ChatEntity {
	
	private int id;
	private String logId;
	private String toId;
	private String chat;

	private String chatC;

	private String name;
	private String toname;
	
	
	public ChatEntity() {

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChat() {
		return chat;
	}

	public void setChat(String chat) {
		this.chat = chat;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getToname() {
		return toname;
	}

	public void setToname(String toname) {
		this.toname = toname;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}


	public String getChatC() {
		return chatC;
	}

	public void setChatC(String chatC) {
		this.chatC = chatC;
	}

	
}
