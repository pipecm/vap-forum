package com.vanhack.forum;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.IsNull;
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
import com.vanhack.forum.util.UserCodes;
import com.vanhack.forum.util.ForumConstants.TestConstants;

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
	
	private static final String BASE_URI = "/api/user";
	
	@Before
    public void setUp() {
    	mock = MockMvcBuilders
                	.webAppContextSetup(context)
                	.apply(springSecurity())
                	.build();
    }
	
	@Test
	public void givenNoUsers_whenGetUsers_thenStatusNoContent() throws Exception {
		List<User> emptyList = new ArrayList<User>();
		
		given(service.getAllUsers()).willReturn(emptyList);
		
		mock.perform(get(getUri())
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_NO_USERS_FOUND_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(UserCodes.USER_NO_USERS_FOUND_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
	}
	
	@Test
	public void whenGetAllUsers_thenReturnJson() throws Exception {
		
		User myUser = new User();
		myUser.setNickname("pipecm");
		myUser.setEmail("pipecm@gmail.com");
		
		List<User> allUsers = Arrays.asList(myUser);
		
		given(service.getAllUsers()).willReturn(allUsers);
		
		mock.perform(get(getUri())
			.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
			.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
			.andExpect(jsonPath("$.responseMessage", is(MessageFormat.format(UserCodes.USER_FIND_ALL_SUCCESS_MESSAGE, allUsers.size()))))
			.andExpect(jsonPath("$.responseContent", hasSize(1)))
			.andExpect(jsonPath("$.responseContent[0].nickname", is(myUser.getNickname())))
			.andExpect(jsonPath("$.responseContent[0].email", is(myUser.getEmail())));
		
	}
	
	@Test
	public void whenFindByNickname_thenReturnUserJson() throws Exception {
		
		User myUser = new User();
		myUser.setNickname("pipecm");
		myUser.setEmail("pipecm@gmail.com");
		
		String keyword = "pipecm";
		given(service.findByNickname(keyword)).willReturn(myUser);
		
		mock.perform(get("/api/user/find")
			.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
			.contentType(MediaType.APPLICATION_JSON)
			.param("nickname", "pipecm"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
			.andExpect(jsonPath("$.responseMessage", is(MessageFormat.format(UserCodes.USER_FIND_BY_NICKNAME_SUCCESS_MESSAGE, keyword))))
			.andExpect(jsonPath("$.responseContent", hasSize(1)))
			.andExpect(jsonPath("$.responseContent[0].nickname", is(myUser.getNickname())))
			.andExpect(jsonPath("$.responseContent[0].email", is(myUser.getEmail())));
		
	}
	
	private String getUri() {
		return BASE_URI;
	}
	
//	private String getUri(String name) {
//		return BASE_URI + "/" + name;
//	}
	
}
