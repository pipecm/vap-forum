package com.vanhack.forum;

import java.util.Date;

import com.vanhack.forum.dto.Category;
import com.vanhack.forum.dto.Post;
import com.vanhack.forum.dto.Topic;
import com.vanhack.forum.dto.User;

public class TestObjects {
	
	private TestObjects() {
		
	}
	
	public static User getTestUser() {
		User testUser = new User();
		//testUser.setId(1L);
		testUser.setNickname("test_user");
		testUser.setEmail("test@vanhack.com");
		testUser.setPassword("testuser");
		return testUser;
	}
	
	public static Category getTestCategory() {
		Category testCategory = new Category();
		//testCategory.setId(1L);
		testCategory.setName("testing");
    	return testCategory;
    }
	
	public static Topic getTestTopic() {
		Date now = new Date();
		Topic testTopic = new Topic();
		//testTopic.setId(1L);
		testTopic.setTitle("Test Topic");
		testTopic.setCreationDate(now);
		testTopic.setLastUpdate(now);
		return testTopic;
	}
	
	public static Post getTestPost() {
		Date now = new Date();
		Post testPost = new Post();
		//testPost.setId(1L);
		testPost.setPostContent("This post contains some useful information");
		testPost.setCreationDate(now);
		testPost.setLastUpdate(now);
		return testPost;
	}
}
