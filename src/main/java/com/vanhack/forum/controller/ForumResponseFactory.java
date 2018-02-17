package com.vanhack.forum.controller;

import com.vanhack.forum.exception.ForumException;

public class ForumResponseFactory {
	
	public static enum ResponseType {
		USER_RESPONSE, 
		CATEGORY_RESPONSE, 
		TOPIC_RESPONSE, 
		POST_RESPONSE
	};
	
	private ForumResponseFactory() {
		
	}
	
	public static ForumResponse create(ResponseType responseType, int code, String message) {
		switch(responseType) {
			case USER_RESPONSE:
				return new UserResponse(code, message);
			case CATEGORY_RESPONSE:
				return new CategoryResponse(code, message);
			case TOPIC_RESPONSE:
				
			case POST_RESPONSE:
				
			default:
				throw new IllegalArgumentException("Invalid response type");
		}
	}
	
	public static ForumResponse create(ResponseType responseType, ForumException exception) {
		switch(responseType) {
			case USER_RESPONSE:
				return new UserResponse(exception);
			case CATEGORY_RESPONSE:
				return new CategoryResponse(exception);
			case TOPIC_RESPONSE:
				
			case POST_RESPONSE:
				
			default:
				throw new IllegalArgumentException("Invalid response type");
		}
	}
}
