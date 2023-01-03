package com.comittedpeople.englishlearningweb.api.v1.model;

import java.time.LocalDateTime;

public class ChatRoomMessageDTO {

	private Long id;
	
	private String content;
	
	private String timeStamp;
	
	private Long userSentID;
	
	private String userSentName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getUserSentID() {
		return userSentID;
	}

	public void setUserSentID(Long userSentID) {
		this.userSentID = userSentID;
	}

	public String getUserSentName() {
		return userSentName;
	}

	public void setUserSentName(String userSentName) {
		this.userSentName = userSentName;
	}
}
