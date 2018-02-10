package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

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
		
		Mockito.when(userDao.findByNickname(testUser.getNickname()))
			.thenReturn(testUser);
		Mockito.when(userDao.findByEmail(testUser.getEmail()))
			.thenReturn(testUser);	
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
}
