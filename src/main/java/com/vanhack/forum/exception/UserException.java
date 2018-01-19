package com.vanhack.forum.exception;

public class UserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int code;
	
	public UserException(int code, String message) {
		super("Code " + code + ": " + message);
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
}