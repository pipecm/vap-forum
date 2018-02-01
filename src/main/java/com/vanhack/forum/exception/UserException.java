package com.vanhack.forum.exception;

public class UserException extends ForumException {
	
	private static final long serialVersionUID = 1L;

	public UserException(int code, String message) {
		super(code, message);
	}

}
