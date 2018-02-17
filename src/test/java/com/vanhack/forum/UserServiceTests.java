package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VapForumApplication.class, TestingRepository.class })
public class UserServiceTests {
	
	@TestConfiguration
    static class UserServiceTestContextConfiguration {
		@Bean
        public UserService getService() {
			return new UserService();
		}
	}
	
	@MockBean
	private UserDAO userDao;
	
	@Autowired
	private UserService userService;
	
	@Before
	public void setUp() {
		User testUser = new User();
		testUser.setNickname("test_user");
		testUser.setEmail("test@vanhack.com");
		testUser.setPassword("testuser");
		
		User anotherUser = new User();
		anotherUser.setNickname("another_user");
		anotherUser.setEmail("another@vanhack.com");
		anotherUser.setPassword("anotheruser");
		
		List<User> userList = Arrays.asList(testUser, anotherUser);
		
		Mockito.when(userDao.findByNicknameContaining(testUser.getNickname()))
			.thenReturn(Arrays.asList(testUser));
		Mockito.when(userDao.findByEmailContaining(testUser.getEmail()))
			.thenReturn(Arrays.asList(testUser));	
		Mockito.when(userDao.findAll())
			.thenReturn(userList);
	}
	
	@Test
	public void whenFindAll_thenReturnAllUsers() {
		assertThat(userService.getAllUsers()).isNotEmpty();
	}
	
	@Test
	public void whenFindByNickname_thenReturnUser() {
		String nickname = "test_user";
		User found = userService.findByNickname(nickname);
		
		assertThat(found.getNickname()).isEqualTo(nickname);
	}
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		String email = "test@vanhack.com";
		User found = userService.findByEmail(email);
		
		assertThat(found.getEmail()).isEqualTo(email);
	}
	
	@Test
	public void whenFindingNotExistingNicknameUser_thenError() {
		assertThat(userService.findByNickname("not_found")).isNull();
	}
	
	@Test
	public void whenFindingNotExistingEmailUser_thenError() {
		
	}
}
