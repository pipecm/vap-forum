package com.vanhack.forum.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:category.properties")
public class CategoryCodes {
	
	@Value("${category.success.code}")
	public int CATEGORY_SUCCESS_CODE;
	
	@Value("${category.success.message}")
	public String CATEGORY_SUCCESS_MESSAGE;
	
	@Value("${category.insert.success.message}")
	public String CATEGORY_INSERT_SUCCESS_MESSAGE;
	
	@Value("${category.findall.success.message}")
	public String CATEGORY_FIND_ALL_SUCCESS_MESSAGE;
	
	@Value("${category.findbyname.success.message}")
	public String CATEGORY_FIND_BY_NAME_SUCCESS_MESSAGE;
	
	@Value("${category.name.empty.code}")
	public int CATEGORY_EMPTY_NAME_CODE;
	
	@Value("${category.name.empty.message}")
	public String CATEGORY_EMPTY_NAME_MESSAGE;
	
	@Value("${category.name.exists.code}")
	public int CATEGORY_NAME_ALREADY_EXISTS_CODE;
	
	@Value("${category.name.exists.message}")
	public String CATEGORY_NAME_ALREADY_EXISTS_MESSAGE;
	
	@Value("${category.validation.error.code}")
	public int CATEGORY_VALIDATION_ERROR_CODE;
	
	@Value("${category.validation.error.message}")
	public String CATEGORY_VALIDATION_ERROR_MESSAGE;
	
	@Value("${category.notfound.code}")
	public int CATEGORY_NOT_FOUND_CODE;
	
	@Value("${category.notfound.message}")
	public String CATEGORY_NOT_FOUND_MESSAGE;
	
	@Value("${category.name.notfound.code}")
	public int CATEGORY_NAME_NOT_FOUND_CODE;
	
	@Value("${category.name.notfound.message}")
	public String CATEGORY_NAME_NOT_FOUND_MESSAGE;
	
	@Value("${category.unexpected.code}")
	public int CATEGORY_UNEXPECTED_ERROR_CODE;
	
	@Value("${category.unexpected.message}")
	public String CATEGORY_UNEXPECTED_ERROR_MESSAGE;
}
