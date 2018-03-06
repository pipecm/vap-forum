package com.vanhack.forum;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanhack.forum.controller.UserController;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.ForumExceptionFactory;
import com.vanhack.forum.exception.ForumExceptionFactory.ExceptionType;
import com.vanhack.forum.security.AppSecurityConfig;
import com.vanhack.forum.service.UserService;
import com.vanhack.forum.util.ForumConstants.TestConstants;
import com.vanhack.forum.util.ForumConstants.UserConstants;
import com.vanhack.forum.util.UserCodes;

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
	
	private ObjectMapper mapper = new ObjectMapper();
	
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
	public void whenFindByNickname_thenReturnUserJsonList() throws Exception {
			
		User testOne = TestObjects.getTestUser();
		User testTwo = TestObjects.getTestUser();
		testOne.setNickname("test_one");
		testTwo.setNickname("test_two");
		List<User> usersFound = Arrays.asList(testOne, testTwo);
		
		String keyword = "test";
		given(service.findByNicknameContaining(keyword)).willReturn(usersFound);
		
		mock.perform(get("/api/user/findbynickname")
			.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
			.contentType(MediaType.APPLICATION_JSON)
			.param("nickname", keyword))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
			.andExpect(jsonPath("$.responseMessage", 
					is(MessageFormat.format(UserCodes.USER_FIND_BY_NICKNAME_SUCCESS_MESSAGE, 
											usersFound.size(), 
											keyword))))
			.andExpect(jsonPath("$.responseContent", hasSize(2)))
			.andExpect(jsonPath("$.responseContent[0].nickname", containsString(keyword)))
			.andExpect(jsonPath("$.responseContent[1].nickname", containsString(keyword)));
		
	}
	
	@Test
	public void whenFindByEmail_andUserExists_thenReturnUserJson() throws Exception {
		String email = "test@vanhack.com";
		User testUser = TestObjects.getTestUser();
		
		given(service.findByEmail(email)).willReturn(testUser);
		
		mock.perform(get("/api/user/findbyemail")
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", 
						is(MessageFormat.format(UserCodes.USER_FIND_BY_EMAIL_SUCCESS_MESSAGE, 
												email))))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].email", is(email)));
	}
	
	@Test
	public void whenFindingNotExistingNicknameUser_thenReturnEmptyJson() throws Exception {
		String keyword = "not_found";
		List<User> emptyList = new ArrayList<User>();
		
		given(service.findByNicknameContaining(keyword)).willReturn(emptyList);
		
		mock.perform(get("/api/user/findbynickname")
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.contentType(MediaType.APPLICATION_JSON)
				.param("nickname", keyword))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_NICKNAME_NOT_FOUND_CODE)))
				.andExpect(jsonPath("$.responseMessage", 
						is(MessageFormat.format(UserCodes.USER_NICKNAME_NOT_FOUND_MESSAGE,
												keyword))))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
	}
	
	@Test
	public void whenFindingNotExistingEmailUser_thenReturnEmptyJson() throws Exception {
		String email = "notfound@vanhack.com";
		
		given(service.findByEmail(email)).willReturn(null);
		
		mock.perform(get("/api/user/findbyemail")
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_EMAIL_NOT_FOUND_CODE)))
				.andExpect(jsonPath("$.responseMessage", 
						is(MessageFormat.format(UserCodes.USER_EMAIL_NOT_FOUND_MESSAGE, 
												email))))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
	}
	
	@Test
	public void whenInsertACorrectUser_thenOKandReturnSavedUserJson() throws Exception {
		User testUser = TestObjects.getTestUser();
		User savedUser = TestObjects.getTestUser();
		savedUser.setId(1L);
		
		given(service.addUser(testUser)).willReturn(savedUser);
		
		mock.perform(post(getUri())
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testUser))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(UserCodes.USER_INSERT_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].id", is(IsNull.notNullValue())))
				.andExpect(jsonPath("$.responseContent[0].nickname", is(testUser.getNickname())))
				.andExpect(jsonPath("$.responseContent[0].email", is(testUser.getEmail())));
				
	}
	
	@Test
	public void whenUserIsUpdated_thenReturnedAnUpdatedJson() throws Exception {
		User testUser = TestObjects.getTestUser();
		User savedUser = TestObjects.getTestUser();
		savedUser.setId(1L);
		
		given(service.addUser(testUser)).willReturn(savedUser);
		
		mock.perform(post(getUri())
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testUser))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(UserCodes.USER_INSERT_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].id", is(IsNull.notNullValue())))
				.andExpect(jsonPath("$.responseContent[0].nickname", is(testUser.getNickname())))
				.andExpect(jsonPath("$.responseContent[0].email", is(testUser.getEmail())));
		
		savedUser.setNickname("updated");
		savedUser.setEmail("updated@vanhack.com");
		
		User updatedUser = TestObjects.getTestUser();
		updatedUser.setId(savedUser.getId());
		updatedUser.setNickname("updated");
		updatedUser.setEmail("updated@vanhack.com");
		updatedUser.setPassword(savedUser.getPassword());
		
		given(service.updateUser(savedUser)).willReturn(updatedUser);
		
		mock.perform(put(getUri())
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(savedUser))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(UserCodes.USER_UPDATE_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].id", is(savedUser.getId().intValue())))
				.andExpect(jsonPath("$.responseContent[0].nickname", is(savedUser.getNickname())))
				.andExpect(jsonPath("$.responseContent[0].email", is(savedUser.getEmail())));
		
	}
	
	@Test
	public void whenUserIsDeleted_thenUserIsRemoved() throws Exception {
		User testUser = TestObjects.getTestUser();
		User savedUser = TestObjects.getTestUser();
		long id = 1L;
		savedUser.setId(id);
		
		given(service.addUser(testUser)).willReturn(savedUser);
		
		mock.perform(post(getUri())
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testUser))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(UserCodes.USER_INSERT_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", hasSize(1)))
				.andExpect(jsonPath("$.responseContent[0].id", is(IsNull.notNullValue())))
				.andExpect(jsonPath("$.responseContent[0].nickname", is(testUser.getNickname())))
				.andExpect(jsonPath("$.responseContent[0].email", is(testUser.getEmail())));
		
		given(service.deleteUser(id)).willReturn(UserCodes.USER_SUCCESS_CODE);
		
		mock.perform(delete(getUri(id))
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.contentType(MediaType.APPLICATION_JSON)
				.param("id", Long.toString(id)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode", is(UserCodes.USER_SUCCESS_CODE)))
				.andExpect(jsonPath("$.responseMessage", is(UserCodes.USER_DELETE_SUCCESS_MESSAGE)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
	}
	
	@Test
	public void whenNicknameIsEmpty_thenError() throws Exception {
		testInvalidAttributes(UserTestType.EMPTY_NICKNAME);
	}
	
	@Test
	public void whenEmailIsEmpty_thenError() throws Exception {
		testInvalidAttributes(UserTestType.EMPTY_EMAIL);
	}
	
	@Test
	public void whenPasswordIsEmpty_thenError() throws Exception {
		testInvalidAttributes(UserTestType.EMPTY_PASSWORD);
	}
	
	@Test
	public void whenNicknameHasLessThan5Characters_thenError() throws Exception {
		testInvalidAttributes(UserTestType.SHORTER_NICKNAME);
	}
	
	@Test
	public void whenPasswordHasLessThan5Characters_thenError() throws Exception {
		testInvalidAttributes(UserTestType.SHORTER_PASSWORD);
	}
	
	@Test
	public void whenNicknameHasMoreThan20Characters_thenError() throws Exception {
		testInvalidAttributes(UserTestType.LONGER_NICKNAME);
	}
	
	@Test
	public void whenEmailIsInvalid_thenError() throws Exception {
		testInvalidAttributes(UserTestType.INVALID_EMAIL);
	}
	
	private void testInvalidAttributes(UserTestType testType) throws Exception {	
		User testUser = TestObjects.getTestUser();
		int exceptionCode = 0;
		String exceptionMessage = "";
		
		switch(testType) {
			case EMPTY_NICKNAME:
				testUser.setNickname("");
				exceptionCode = UserCodes.USER_EMPTY_NICKNAME_CODE;
				exceptionMessage = UserCodes.USER_EMPTY_NICKNAME_MESSAGE;
				break;
			case EMPTY_EMAIL:
				testUser.setEmail("");
				exceptionCode = UserCodes.USER_EMPTY_EMAIL_CODE;
				exceptionMessage = UserCodes.USER_EMPTY_EMAIL_MESSAGE;
				break;
			case EMPTY_PASSWORD:
				testUser.setPassword("");
				exceptionCode = UserCodes.USER_EMPTY_PASSWORD_CODE;
				exceptionMessage = UserCodes.USER_EMPTY_PASSWORD_MESSAGE;
				break;
			case SHORTER_NICKNAME:
				testUser.setNickname("test");
				exceptionCode = UserCodes.USER_INVALID_NICKNAME_CODE;
				exceptionMessage = MessageFormat.format(UserCodes.USER_INVALID_NICKNAME_MESSAGE,
															UserConstants.USER_NICKNAME_MIN_LENGTH,
															UserConstants.USER_NICKNAME_MAX_LENGTH);
				break;
			case SHORTER_PASSWORD:
				testUser.setPassword("123");
				exceptionCode = UserCodes.USER_INVALID_PASSWORD_CODE;
				exceptionMessage = MessageFormat.format(UserCodes.USER_INVALID_PASSWORD_MESSAGE,
															UserConstants.USER_PASSWORD_MIN_LENGTH);
				break;
			case LONGER_NICKNAME:
				testUser.setNickname("test_test_test_test_test");
				exceptionCode = UserCodes.USER_INVALID_NICKNAME_CODE;
				exceptionMessage = MessageFormat.format(UserCodes.USER_INVALID_NICKNAME_MESSAGE,
															UserConstants.USER_NICKNAME_MIN_LENGTH,
															UserConstants.USER_NICKNAME_MAX_LENGTH);
				break;
			case INVALID_EMAIL:
				testUser.setEmail("test.test.com");
				exceptionCode = UserCodes.USER_INVALID_EMAIL_CODE;
				exceptionMessage = UserCodes.USER_INVALID_EMAIL_MESSAGE;
				break;
			default:
				break;
	}
		
		given(service.addUser(testUser))
			.willThrow(ForumExceptionFactory.create(ExceptionType.USER_EXCEPTION, 
													exceptionCode, exceptionMessage))
			.willReturn(null);
		 
		mock.perform(post(getUri())
				.with(user(TestConstants.USER).password(TestConstants.PASSWORD).roles(TestConstants.ROLES))
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(testUser))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.responseCode", is(exceptionCode)))
				.andExpect(jsonPath("$.responseMessage", is(exceptionMessage)))
				.andExpect(jsonPath("$.responseContent", is(IsNull.nullValue())));
		 
	}
	
	private String getUri() {
		return BASE_URI;
	}
	
	private String getUri(Long id) {
		return BASE_URI + "/" + Long.toString(id);
	}
	
}
