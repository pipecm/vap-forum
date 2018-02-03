package com.vanhack.forum;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vanhack.forum.controller.UserController;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.security.AppSecurityConfig;
import com.vanhack.forum.service.UserService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { VapForumApplication.class, TestingRepository.class })
@WebMvcTest(UserController.class)
@Import(AppSecurityConfig.class)
@WebAppConfiguration
public class UserControllerTests {

	@Autowired
    private WebApplicationContext context;
	
	private MockMvc mock;

	@MockBean
	private UserService service;
	
	@Before
    public void setUp() {
    	mock = MockMvcBuilders
                	.webAppContextSetup(context)
                	.apply(springSecurity())
                	.build();
    }
	
	@Test
	public void whenGetAllUsers_thenReturnJson() throws Exception {
		
		User myUser = new User();
		myUser.setNickname("pipecm");
		myUser.setEmail("pipecm@gmail.com");
		
		List<User> allUsers = Arrays.asList(myUser);
		
		given(service.getAllUsers()).willReturn(allUsers);
		
		mock.perform(get("/api/user")
			.with(user("pipecm").password("vanhack").roles("ADMIN"))
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].nickname", is(myUser.getNickname())))
			.andExpect(jsonPath("$[0].email", is(myUser.getEmail())));
	}
	
}
