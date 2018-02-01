package com.vanhack.forum;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VapForumApplicationTests {
	
	@Autowired
	private UserService userService;
	
	private static final Logger log = LogManager.getLogger(VapForumApplicationTests.class);

	/* User tests */
	
	@Test
	public void a_insertTestUser() {
		try {
			User user = getTestUser();
			assertTrue(userService.addUser(user) == 0);
		} catch(ForumException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	@Test
	public void b_getListOfAllUsers() {
		Iterable<User> list = userService.getAllUsers();
		assertTrue(list instanceof Iterable<?> || list == null);
	}
	
	@Test
	public void c_getUserWithIntegerId() {
		assertTrue(userService.findById(1L) instanceof User);
	}
	
	@Test
	public void d_getUserWithNickname() {
		assertTrue(userService.findByNickname("vanhack") != null);
	}
	
	@Test
	public void e_userNotFoundWithNickname() {
		assertTrue(userService.findByNickname("pipecm") == null);
	}
	
	@Test
	public void f_getUserWithEmail() {
		assertTrue(userService.findByEmail("vanhack@vanhack.com") != null);
	}
	
	@Test
	public void g_userNotFoundWithEmail() {
		assertTrue(userService.findByEmail("pipecm@gmail.com") == null);
	}
	
	@Test(expected = ForumException.class)
	public void h_insertUserWithExistingNickname() throws ForumException {
		User user = getTestUser();
		user.setNickname("vanhack");
		assertFalse(userService.addUser(user) == 0);
	}
	
	@Test(expected = ForumException.class)
	public void i_insertUserWithExistingEmail() throws ForumException {
		User user = getTestUser();
		user.setEmail("vanhack@vanhack.com");
		assertFalse(userService.addUser(user) == 0);
	}
	
	@Test(expected = ForumException.class)
	public void j_insertUserWithEmptyNickname() throws ForumException {
		User user = getTestUser();
		user.setNickname("");
		assertFalse(userService.addUser(user) == 0);
	}
	
	@Test(expected = ForumException.class)
	public void k_insertUserWithEmptyEmail() throws ForumException {
		User user = getTestUser();
		user.setEmail("");
		assertFalse(userService.addUser(user) == 0);
	}
	
	@Test(expected = ForumException.class)
	public void l_insertUserWithEmptyPassword() throws ForumException {
		User user = getTestUser();
		user.setPassword("");
		assertFalse(userService.addUser(user) == 0);
	}
	
	@Test(expected = ForumException.class)
	public void m_insertUserWithInvalidEmail() throws ForumException {
		User user = getTestUser();
		user.setNickname("test");
		user.setEmail("vanhack.vanhack.com");
		assertFalse(userService.addUser(user) == 0);
	}
	
	@Test
	public void n_updateUser() {
		try {
			User user = userService.findByNickname("vanhack");
			user.setNickname("felipe");
			assertTrue(userService.updateUser(user) == 0);
		} catch(ForumException e) {
			log.error(e.getMessage(), e);
		}	
	}
	
	@Test
	public void o_deleteUser() {
		try {
			User user = userService.findByNickname("felipe");
			assertTrue(userService.deleteUser(user.getId()) == 0);
		} catch(ForumException e) {
			log.error(e.getMessage(), e);
		}	
	}
	
	private static User getTestUser() {
		User user = new User();
		user.setNickname("vanhack");
		user.setPassword("vanhack");
		user.setEmail("vanhack@vanhack.com");
		return user;
	} 
	
}
