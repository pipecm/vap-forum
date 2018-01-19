package com.vanhack.forum.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Messages {

	@Value("${user.nickname.exists.message}")
	public static String USER_NICKNAME_ALREADY_EXISTS;
	
	@Value("${user.email.exists.message}")
	public static String USER_EMAIL_ALREADY_EXISTS;
	
	@Value("${user.nickname.invalid.message}")
	public static String USER_INVALID_NICKNAME;
	
	@Value("${user.email.invalid.message}")
	public static String USER_INVALID_EMAIL;
	
	@Value("${user.password.invalid.message}")
	public static String USER_INVALID_PASSWORD;
	
	@Value("${user.nickname.null.message}")
	public static String USER_NULL_NICKNAME;
	
	@Value("${user.email.null.message}")
	public static String USER_NULL_EMAIL;
	
	@Value("${user.password.null.message}")
	public static String USER_NULL_PASSWORD;
}
