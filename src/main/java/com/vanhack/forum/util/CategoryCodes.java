package com.vanhack.forum.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:category.properties")
public class CategoryCodes {
	
	public static int CATEGORY_SUCCESS_CODE;
	public static String CATEGORY_SUCCESS_MESSAGE;
	public static String CATEGORY_INSERT_SUCCESS_MESSAGE;
	public static String CATEGORY_FIND_ALL_SUCCESS_MESSAGE;
	public static String CATEGORY_FIND_BY_NAME_SUCCESS_MESSAGE;
	public static int CATEGORY_EMPTY_NAME_CODE;
	public static String CATEGORY_EMPTY_NAME_MESSAGE;
	public static int CATEGORY_NAME_ALREADY_EXISTS_CODE;
	public static String CATEGORY_NAME_ALREADY_EXISTS_MESSAGE;
	public static int CATEGORY_VALIDATION_ERROR_CODE;
	public static String CATEGORY_VALIDATION_ERROR_MESSAGE;
	public static int CATEGORY_NOT_FOUND_CODE;
	public static String CATEGORY_NOT_FOUND_MESSAGE;
	public static int CATEGORY_NAME_NOT_FOUND_CODE;
	public static String CATEGORY_NAME_NOT_FOUND_MESSAGE;
	public static int CATEGORY_UNEXPECTED_ERROR_CODE;
	public static String CATEGORY_UNEXPECTED_ERROR_MESSAGE;
	
	@Value("${category.success.code}")
	public void setCategorySuccessCode(int code) {
		CATEGORY_SUCCESS_CODE = code;
	}
	
	@Value("${category.success.message}")
	public void setCategorySuccessMessage(String message) {
		CATEGORY_SUCCESS_MESSAGE = message;
	}
	
	@Value("${category.insert.success.message}")
	public void setCategoryInsertSuccessMessage(String message) {
		CATEGORY_INSERT_SUCCESS_MESSAGE = message;
	}
	
	@Value("${category.findall.success.message}")
	public void setCategoryFindAllSuccessMessage(String message) {
		CATEGORY_FIND_ALL_SUCCESS_MESSAGE = message;
	}
	
	@Value("${category.findbyname.success.message}")
	public void setCategoryFindByNameSuccessMessage(String message) {
		CATEGORY_FIND_BY_NAME_SUCCESS_MESSAGE = message;
	}
	
	@Value("${category.name.empty.code}")
	public void setCategoryNameEmptyCode(int code) {
		CATEGORY_EMPTY_NAME_CODE = code;
	}
	
	@Value("${category.name.empty.message}")
	public void setCategoryEmptyNameMessage(String message) {
		CATEGORY_EMPTY_NAME_MESSAGE = message;
	}
	
	@Value("${category.name.exists.code}")
	public void setCategoryNameAlreadyExistsCode(int code) {
		CATEGORY_NAME_ALREADY_EXISTS_CODE = code;
	}
	
	@Value("${category.name.exists.message}")
	public void setCategoryNameAlreadyExistsMessage(String message) {
		CATEGORY_NAME_ALREADY_EXISTS_MESSAGE = message;
	}
	
	@Value("${category.validation.error.code}")
	public void setCategoryValidationErrorCode(int code) {
		CATEGORY_VALIDATION_ERROR_CODE = code;
	}
	
	@Value("${category.validation.error.message}")
	public void setCategoryValidationErrorMessage(String message) {
		CATEGORY_VALIDATION_ERROR_MESSAGE = message;
	}
	
	@Value("${category.notfound.code}")
	public void setCategoryNotFoundCode(int code) {
		CATEGORY_NOT_FOUND_CODE = code;
	}
	
	@Value("${category.notfound.message}")
	public void setCategoryNotFoundMessage(String message) {
		CATEGORY_NOT_FOUND_MESSAGE = message;
	}
	
	@Value("${category.name.notfound.code}")
	public void setCategoryNameNotFoundCode(int code) {
		CATEGORY_NAME_NOT_FOUND_CODE = code;
	}
	
	@Value("${category.name.notfound.message}")
	public void setCategoryNameNotFoundMessage(String message) {
		CATEGORY_NAME_NOT_FOUND_MESSAGE = message;
	}
	
	@Value("${category.unexpected.code}")
	public void setCategoryUnexpectedErrorCode(int code) {
		CATEGORY_UNEXPECTED_ERROR_CODE = code;
	}
	
	@Value("${category.unexpected.message}")
	public void setCategoryUnexpectedErrorMessage(String message) {
		CATEGORY_UNEXPECTED_ERROR_MESSAGE = message;
	}
}
