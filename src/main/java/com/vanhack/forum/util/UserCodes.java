package com.vanhack.forum.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:user.properties")
public class UserCodes {
	
	public static int USER_SUCCESS_CODE;
	public static String USER_SUCCESS_MESSAGE;
	public static String USER_FIND_ALL_SUCCESS_MESSAGE;
	public static String USER_FIND_BY_NICKNAME_SUCCESS_MESSAGE;
	public static String USER_FIND_BY_EMAIL_SUCCESS_MESSAGE;
	
	public static int USER_NO_USERS_FOUND_CODE;
	public static String USER_NO_USERS_FOUND_MESSAGE; 
	public static int USER_NICKNAME_NOT_FOUND_CODE;
	public static String USER_NICKNAME_NOT_FOUND_MESSAGE;
	public static int USER_EMAIL_NOT_FOUND_CODE;
	public static String USER_EMAIL_NOT_FOUND_MESSAGE;
	
	
	@Value("${user.success.code}")
	public void setSuccessCode(int code) {
		USER_SUCCESS_CODE = code;
	}
	
	@Value("${user.success.message}")
	public void setUserSuccessMessage(String message) {
		USER_SUCCESS_MESSAGE = message;
	}
	
	@Value("${user.findall.success.message}")
	public void setFindAllSuccessMessage(String message) {
		USER_FIND_ALL_SUCCESS_MESSAGE = message;
	}
	
	@Value("${user.findbynickname.success.message}")
	public void setFindByNicknameSuccessMessage(String message) {
		USER_FIND_BY_NICKNAME_SUCCESS_MESSAGE = message;
	}
	
	@Value("${user.findbyemail.success.message}")
	public void setFindByEmailSuccessMessage(String message) {
		USER_FIND_BY_EMAIL_SUCCESS_MESSAGE = message;
	}
	
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

	@Value("${user.no.users.found.code}")
	public void setUserNoUsersFoundCode(int code) {
		USER_NO_USERS_FOUND_CODE = code;
	}
	
	@Value("${user.no.users.found.message}")
	public void setUserNoUsersFoundMessage(String message) {
		USER_NO_USERS_FOUND_MESSAGE = message;
	}
	
	@Value("${user.nickname.notfound.code}")
	public void setUserNicknameNotFoundCode(int code) {
		USER_NICKNAME_NOT_FOUND_CODE = code;
	}
	
	@Value("${user.nickname.notfound.message}")
	public void setUserNicknameNotFoundMessage(String message) {
		USER_NICKNAME_NOT_FOUND_MESSAGE = message;
	}
	
	@Value("${user.email.notfound.code}")
	public void setUserEmailNotFoundCode(int code) {
		USER_EMAIL_NOT_FOUND_CODE = code;
	}
	
	@Value("${user.email.notfound.message}")
	public void setUserEmailNotFoundMessage(String message) {
		USER_EMAIL_NOT_FOUND_MESSAGE = message;
	}
	
	@Value("${user.unexpected.code}")
	public int USER_UNEXPECTED_ERROR_CODE;
	
	@Value("${user.unexpected.message}")
	public String USER_UNEXPECTED_ERROR_MESSAGE;

}
