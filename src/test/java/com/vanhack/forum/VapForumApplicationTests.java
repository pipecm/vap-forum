package com.vanhack.forum;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserAlreadyExistsException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VapForumApplicationTests {
	
	private UserDAO userDao;
	
	private static final Logger log = Logger.getLogger(VapForumApplicationTests.class);

	@Test
	public void contextLoads() {
		
	}

	/* User tests */
	
	@Test
	public void getListOfAllUsers() {
		assertTrue(userDao.getAllUsers() instanceof List<?>);
	}
	
	@Test
	public void getUserWithIntegerId() {
		assertTrue(userDao.getUserById(1) instanceof User);
	}
	
	@Test
	public void getUserWithNickname() {
		assertTrue(userDao.getUserByNickname("pipecm") instanceof User);
	}
	
	@Test
	public void getUserWithEmail() {
		assertTrue(userDao.getUserByEmail("pipecm@gmail.com") instanceof User);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void insertUserWithExistingId() {
		userDao.addUser(getTestUser());
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void insertUserWithExistingNickname() {
		User user = new User();
		user.setNickname("vanhack");
		user.setPassword("vanhack");
		user.setEmail("vanhack@vanhack.com");
		userDao.addUser(user);
		
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void insertUserWithExistingEmail() {
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertUserWithNullNickname() {
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertUserWithNullEmail() {
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertUserWithInvalidEmail() {
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void insertUserWithNicknameLongerThanAllowed() {
		
	}
	
	private static User getTestUser() {
		User user = new User();
		user.setId(1);
		user.setNickname("pipecm");
		user.setEmail("pipecm@gmail.com");
		user.setPassword("vanhack");
		return user;
	}
	
}
