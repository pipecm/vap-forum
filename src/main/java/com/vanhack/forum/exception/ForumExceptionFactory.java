package com.vanhack.forum.exception;

public class ForumExceptionFactory {
	
	public static final int USER_EXCEPTION = 1;
	public static final int CATEGORY_EXCEPTION = 2;
	public static final int TOPIC_EXCEPTION = 3;
	public static final int POST_EXCEPTION = 4;
	
	private ForumExceptionFactory() {
		
	}
	
	public static ForumException create(int exceptionType, int code, String message) 
		throws IllegalArgumentException {
		switch(exceptionType) {
			case USER_EXCEPTION:
				return new UserException(code, message);
			case CATEGORY_EXCEPTION:
				return new CategoryException(code, message);
			case TOPIC_EXCEPTION:
				return new TopicException(code, message);
			case POST_EXCEPTION:
				return new PostException(code, message);
			default:
				throw new IllegalArgumentException("Invalid exception type");
		}
	}
}
