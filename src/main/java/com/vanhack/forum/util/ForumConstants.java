package com.vanhack.forum.util;

public class ForumConstants {

	public static final String API_ENDPOINT = "/api";
		
	public static class UserConstants {
		public static final int USER_NICKNAME_MIN_LENGTH = 5;
		public static final int USER_NICKNAME_MAX_LENGTH = 20;
		public static final String USER_GET_ALL_ENDPOINT = "/user";
		public static final String USER_GET_BY_NICKNAME_ENDPOINT = "/user/{nickname}";
		public static final String USER_GET_BY_EMAIL_ENDPOINT = "/user/{email}";
		public static final String USER_ADD_ENDPOINT = "/user";
		public static final String USER_UPDATE_ENDPOINT = "/user";
		public static final String USER_DELETE_ENDPOINT = "/user/{id}";
	}
	
	public static class CategoryConstants {
		public static final String CATEGORY_GET_ALL_ENDPOINT = "/category";
		public static final String CATEGORY_GET_BY_NAME_ENDPOINT = "/category/find"; //"/category/{name}";
		public static final String CATEGORY_ADD_ENDPOINT = "/category";
		public static final String CATEGORY_UPDATE_ENDPOINT = "/category";
		public static final String CATEGORY_DELETE_ENDPOINT = "/category/{id}";
	}
	
	public static class TestConstants {
		public static final String USER = "pipecm";
		public static final String PASSWORD = "vanhack";
		public static final String ROLES = "ADMIN";
	}
	
	public static class Queries {
		public static final String SQL_CATEGORY_CHECK_NAME = "select * from vap_forum_category "
																+ "where id <> ?1 and name = ?2";
		public static final String SQL_USER_CHECK_NICKNAME = "select * from vap_forum_user "
																+ "where id <> ?1 and nickname = ?2";
		public static final String SQL_USER_CHECK_EMAIL = "select * from vap_forum_user "
																+ "where id <> ?1 and email = ?2";
	}
	
	private ForumConstants() {
		
	}
}
