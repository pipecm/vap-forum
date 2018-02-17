package com.vanhack.forum.controller;

import com.vanhack.forum.exception.ForumException;

public class UserResponse extends ForumResponse {
	
	public UserResponse(int responseCode, String responseMessage) {
		super(responseCode, responseMessage);
	}
	
	public UserResponse(ForumException exception) {
		super(exception);
	}
}
