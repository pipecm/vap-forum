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
	public static int USER_EMPTY_NICKNAME_CODE;
	public static String USER_EMPTY_NICKNAME_MESSAGE;
	public static int USER_EMPTY_EMAIL_CODE;
	public static String USER_EMPTY_EMAIL_MESSAGE;
	public static int USER_EMPTY_PASSWORD_CODE;
	public static String USER_EMPTY_PASSWORD_MESSAGE;
	public static int USER_NICKNAME_ALREADY_EXISTS_CODE;
	public static String USER_NICKNAME_ALREADY_EXISTS_MESSAGE;
	public static int USER_EMAIL_ALREADY_EXISTS_CODE;
	public static String USER_EMAIL_ALREADY_EXISTS_MESSAGE;
	public static int USER_INVALID_NICKNAME_CODE;
	public static String USER_INVALID_NICKNAME_MESSAGE;
	public static int USER_INVALID_EMAIL_CODE;
	public static String USER_INVALID_EMAIL_MESSAGE;
	public static int USER_INVALID_PASSWORD_CODE;
	public static String USER_INVALID_PASSWORD_MESSAGE;
	public static int USER_NO_USERS_FOUND_CODE;
	public static String USER_NO_USERS_FOUND_MESSAGE; 
	public static int USER_NICKNAME_NOT_FOUND_CODE;
	public static String USER_NICKNAME_NOT_FOUND_MESSAGE;
	public static int USER_EMAIL_NOT_FOUND_CODE;
	public static String USER_EMAIL_NOT_FOUND_MESSAGE;
	public static int USER_UNEXPECTED_ERROR_CODE;
	public static String USER_UNEXPECTED_ERROR_MESSAGE;
	public static int USER_VALIDATION_ERROR_CODE;
	public static String USER_VALIDATION_ERROR_MESSAGE;
	
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
	public void setUserNicknameEmptyCode(int code) {
		USER_EMPTY_NICKNAME_CODE = code;
	}
	
	@Value("${user.nickname.empty.message}")
	public void setUserEmptyNicknameMessage(String message) {
		USER_EMPTY_NICKNAME_MESSAGE = message;
	}

	@Value("${user.email.empty.code}")
	public void setUserEmptyEmailCode(int code) {
		USER_EMPTY_EMAIL_CODE = code;
	}
	
	@Value("${user.email.empty.message}")
	public void setUserEmptyEmailMessage(String message) {
		USER_EMPTY_EMAIL_MESSAGE = message;
	}
	
	@Value("${user.password.empty.code}")
	public void setUserEmptyPasswordCode(int code) {
		USER_EMPTY_PASSWORD_CODE = code;
	}
	
	@Value("${user.password.empty.message}")
	public void setUserEmptyPasswordMessage(String message) {
		USER_EMPTY_PASSWORD_MESSAGE = message;
	}

	@Value("${user.nickname.exists.code}")
	public void setUserNicknameAlreadyExistsCode(int code) {
		USER_NICKNAME_ALREADY_EXISTS_CODE = code;
	}
	
	@Value("${user.nickname.exists.message}")
	public void setUserNicknameAlreadyExistsMessage(String message) {
		USER_NICKNAME_ALREADY_EXISTS_MESSAGE = message;
	}
	
	@Value("${user.email.exists.code}")
	public void setUserEmailAlreadyExistsCode(int code) {
		USER_EMAIL_ALREADY_EXISTS_CODE = code;
	}
	
	@Value("${user.email.exists.message}")
	public void setUserEmailAlreadyExistsMessage(String message) {
		USER_EMAIL_ALREADY_EXISTS_MESSAGE = message;
	}
	
	@Value("${user.nickname.invalid.code}")
	public void setUserInvalidNicknameCode(int code) {
		USER_INVALID_NICKNAME_CODE = code;
	}
	
	@Value("${user.nickname.invalid.message}")
	public void setUserInvalidNicknameMessage(String message) {
		USER_INVALID_NICKNAME_MESSAGE = message;
	}
	
	@Value("${user.email.invalid.code}")
	public void setUserInvalidEmailCode(int code) {
		USER_INVALID_EMAIL_CODE = code;
	}
	
	@Value("${user.email.invalid.message}")
	public void setUserInvalidEmailMessage(String message) {
		USER_INVALID_EMAIL_MESSAGE = message;
	}
	
	@Value("${user.password.invalid.code}")
	public void setUserInvalidPasswordCode(int code) {
		USER_INVALID_PASSWORD_CODE = code;
	}
	
	@Value("${user.password.invalid.message}")
	public void setUserInvalidPasswordMessage(String message) {
		USER_INVALID_PASSWORD_MESSAGE = message;
	}

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
	
	@Value("${user.validation.error.code}")
	public void setUserValidationErrorCode(int code) {
		USER_VALIDATION_ERROR_CODE = code;
	}
	
	@Value("${user.validation.error.message}")
	public void setUserValidationErrorMessage(String message) {
		USER_VALIDATION_ERROR_MESSAGE = message;
	}
	
	@Value("${user.unexpected.code}")
	public void setUserUnexpectedErrorCode(int code) {
		USER_UNEXPECTED_ERROR_CODE = code;
	}
	
	@Value("${user.unexpected.message}")
	public void setUserUnexpectedErrorMessage(String message) {
		USER_UNEXPECTED_ERROR_MESSAGE = message;
	}

}
