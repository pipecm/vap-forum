package com.vanhack.forum.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Codes {
	
	@Value("${user.nickname.exists.code}")
	public static int USER_NICKNAME_ALREADY_EXISTS;
	
	@Value("${user.email.exists.code}")
	public static int USER_EMAIL_ALREADY_EXISTS;
	
	@Value("${user.nickname.invalid.code}")
	public static int USER_INVALID_NICKNAME;
	
	@Value("${user.email.invalid.code}")
	public static int USER_INVALID_EMAIL;
	
	@Value("${user.password.invalid.code}")
	public static int USER_INVALID_PASSWORD;
	
	@Value("${user.nickname.null.code}")
	public static int USER_NULL_NICKNAME;
	
	@Value("${user.email.null.code}")
	public static int USER_NULL_EMAIL;
	
	@Value("${user.password.null.code}")
	public static int USER_NULL_PASSWORD;
}
