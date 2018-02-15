package com.vanhack.forum.service;

import java.util.List;

import com.vanhack.forum.exception.ForumException;

public abstract class ForumResponse {
	
	private int responseCode;
	
	private String responseMessage;
	
	private List<?> responseContent;
	
	public ForumResponse(int responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public ForumResponse(ForumException exception) {
		this.responseCode = exception.getCode();
		this.responseMessage = exception.getMessage();
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public List<?> getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(List<?> responseContent) {
		this.responseContent = responseContent;
	}

}
