package com.vanhack.forum.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:user.properties")
public class UserErrorCodes {

	@Value("${user.nickname.empty.code}")
	public int USER_EMPTY_NICKNAME_CODE;
	
	@Value("${user.nickname.empty.message}")
	public String USER_EMPTY_NICKNAME_MESSAGE;

	@Value("${user.email.empty.code}")
	public int USER_EMPTY_EMAIL_CODE;
	
	@Value("${user.email.empty.message}")
	public String USER_EMPTY_EMAIL_MESSAGE;
	
	@Value("${user.password.empty.code}")
	public int USER_EMPTY_PASSWORD_CODE;
	
	@Value("${user.password.empty.message}")
	public String USER_EMPTY_PASSWORD_MESSAGE;

	@Value("${user.nickname.exists.code}")
	public int USER_NICKNAME_ALREADY_EXISTS_CODE;
	
	@Value("${user.nickname.exists.message}")
	public String USER_NICKNAME_ALREADY_EXISTS_MESSAGE;
	
	@Value("${user.email.exists.code}")
	public int USER_EMAIL_ALREADY_EXISTS_CODE;
	
	@Value("${user.email.exists.message}")
	public String USER_EMAIL_ALREADY_EXISTS_MESSAGE;
	
	@Value("${user.nickname.invalid.code}")
	public int USER_INVALID_NICKNAME_CODE;
	
	@Value("${user.nickname.invalid.message}")
	public String USER_INVALID_NICKNAME_MESSAGE;
	
	@Value("${user.email.invalid.code}")
	public int USER_INVALID_EMAIL_CODE;
	
	@Value("${user.email.invalid.message}")
	public String USER_INVALID_EMAIL_MESSAGE;
	
	@Value("${user.password.invalid.code}")
	public int USER_INVALID_PASSWORD_CODE;
	
	@Value("${user.password.invalid.message}")
	public String USER_INVALID_PASSWORD_MESSAGE;
	
	@Value("${user.unexpected.code}")
	public int USER_UNEXPECTED_ERROR_CODE;
	
	@Value("${user.unexpected.message}")
	public String USER_UNEXPECTED_ERROR_MESSAGE;
}
