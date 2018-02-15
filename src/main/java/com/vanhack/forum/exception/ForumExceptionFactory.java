package com.vanhack.forum.exception;

public class ForumExceptionFactory {

	public static enum ExceptionType {USER_EXCEPTION, CATEGORY_EXCEPTION, TOPIC_EXCEPTION, POST_EXCEPTION};
	
	private ForumExceptionFactory() {
		
	}
	
	public static ForumException create(ExceptionType exceptionType, int code, String message) 
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
