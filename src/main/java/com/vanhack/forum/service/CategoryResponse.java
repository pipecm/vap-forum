package com.vanhack.forum.service;

import com.vanhack.forum.exception.ForumException;

public class CategoryResponse extends ForumResponse {
	
	public CategoryResponse(int responseCode, String responseMessage) {
		super(responseCode, responseMessage);
	}
	
	public CategoryResponse(ForumException exception) {
		super(exception);
	}
	
}
