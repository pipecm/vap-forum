package com.vanhack.forum.util;

public class ForumConstants {

	public static final String API_ENDPOINT = "/api";
	
	public static final String USER_GET_ALL_ENDPOINT = "/user";
	public static final String USER_GET_BY_NICKNAME_ENDPOINT = "/user/{nickname}";
	public static final String USER_GET_BY_EMAIL_ENDPOINT = "/user/{email}";
	public static final String USER_ADD_ENDPOINT = "/user";
	public static final String USER_UPDATE_ENDPOINT = "/user";
	public static final String USER_DELETE_ENDPOINT = "/user/{id}";
	
	public static final String CATEGORY_GET_ALL_ENDPOINT = "/category";
	public static final String CATEGORY_GET_BY_NAME_ENDPOINT = "/category/{name}";
	public static final String CATEGORY_ADD_ENDPOINT = "/category";
	public static final String CATEGORY_UPDATE_ENDPOINT = "/category";
	public static final String CATEGORY_DELETE_ENDPOINT = "/category/{id}";
		
	
	private ForumConstants() {
		
	}
}
