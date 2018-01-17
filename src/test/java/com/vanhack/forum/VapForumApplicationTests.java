package com.vanhack.forum;

import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VapForumApplicationTests {
	
	private UserDAO userDao;
	private TopicDAO topicDao;

	@Test
	public void contextLoads() {
		
	}

	@Test
	public void getListOfAllUsers() {
		assertTrue(userDao.getAllUsers() instanceof List<?>);
	}
	
	@Test
	public void getListOfAllTopics() {
		assertTrue(topicDao.getAllTopics() instanceof List<?>);
	}
	
	@Test
	public void getUserWithIntegerId() {
		assertTrue(userDao.getUser(1) instanceof User);
	}
	
	@Test
	public void getUserWithNickname() {
		
	}
	
	@Test
	public void insertUserWithExistingId() {
		
	}
	
}
