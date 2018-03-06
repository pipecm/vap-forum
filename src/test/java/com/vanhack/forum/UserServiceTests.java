package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
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
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.exception.UserException;
import com.vanhack.forum.service.UserService;
import com.vanhack.forum.util.ForumConstants.UserConstants;
import com.vanhack.forum.util.UserCodes;

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
		User firstUser = new User();
		firstUser.setId(1L);
		firstUser.setNickname("test_one");
		firstUser.setEmail("test@vanhack.com");
		firstUser.setPassword("testuser");
		
		User secondUser = new User();
		secondUser.setNickname("test_two");
		secondUser.setEmail("test2@vanhack.com");
		secondUser.setPassword("testuser");
		
		User thirdUser = new User();
		thirdUser.setNickname("another_user");
		thirdUser.setEmail("another@vanhack.com");
		thirdUser.setPassword("anotheruser");
		
		User testUser = TestObjects.getTestUser();
		
		User savedUser = TestObjects.getTestUser();
		savedUser.setId(1L);
		
		User updatedUser = new User();
		updatedUser.setId(1L);
		updatedUser.setNickname("test_updated");
		updatedUser.setEmail("updated@vanhack.com");
		updatedUser.setPassword("testuser");
		
		List<User> allUsers = Arrays.asList(firstUser, secondUser, thirdUser);
		List<User> foundUsers = Arrays.asList(firstUser, secondUser);
		
		Mockito.when(userDao.findByNicknameContaining("test"))
			.thenReturn(foundUsers);
		Mockito.when(userDao.findByEmail("test@vanhack.com"))
			.thenReturn(firstUser);
		Mockito.when(userDao.findAll())
			.thenReturn(allUsers);
		Mockito.when(userDao.save(testUser))
			.thenReturn(savedUser);
		Mockito.when(userDao.save(updatedUser))
			.thenReturn(updatedUser);
		Mockito.when(userDao.findById(1L))
			.thenReturn(null);
	}
	
	@Test
	public void whenFindAll_thenReturnAllUsers() {
		List<User> allUsers = userService.getAllUsers();
		assertThat(allUsers).isNotEmpty();
		assertThat(allUsers.size()).isEqualTo(3);
	}
	
	@Test
	public void whenFindByNicknameContainingText_thenReturnUsersList() {
		String keyword = "test";
		List<User> usersFound = userService.findByNicknameContaining(keyword);
		
		assertThat(usersFound).isNotEmpty();
		for(User found : usersFound) {
			assertThat(found.getNickname()).contains(keyword);
		}	
	}
	
	@Test
	public void whenFindByEmail_andUserExists_thenReturnUser() throws ForumException {
		String email = "test@vanhack.com";
		User found = userService.findByEmail(email);
		
		assertThat(found.getEmail()).isEqualTo(email);
	}
	
	@Test
	public void whenFindingNotExistingNicknameUser_thenError() {
		assertThat(userService.findByNicknameContaining("not_found")).isEmpty();
	}
	
	@Test
	public void whenFindingNotExistingEmailUser_thenError() throws ForumException {
		String email = "test3@vanhack.com";
		User found = userService.findByEmail(email);
		
		assertThat(found).isNull();
	}
	
	@Test
	public void whenAllFieldsAreCorrect_thenOk() {
		testInvalidAttributes(UserTestType.USER_OK);
	}
	
	@Test
	public void whenNicknameIsEmpty_thenError() {
		testInvalidAttributes(UserTestType.EMPTY_NICKNAME);
	}
	
	@Test
	public void whenEmailIsEmpty_thenError() {
		testInvalidAttributes(UserTestType.EMPTY_EMAIL);
	}
	
	@Test
	public void whenPasswordIsEmpty_thenError() {
		testInvalidAttributes(UserTestType.EMPTY_PASSWORD);
	}
	
	@Test
	public void whenNicknameHasLessThan5Characters_thenError() {
		testInvalidAttributes(UserTestType.SHORTER_NICKNAME);
	}
	
	@Test
	public void whenPasswordHasLessThan5Characters_thenError() {
		testInvalidAttributes(UserTestType.SHORTER_PASSWORD);
	}
	
	@Test
	public void whenNicknameHasMoreThan20Characters_thenError() {
		testInvalidAttributes(UserTestType.LONGER_NICKNAME);
	}
	
	@Test
	public void whenEmailIsInvalid_thenError() {
		testInvalidAttributes(UserTestType.INVALID_EMAIL);
	}
	
	@Test
	public void whenUserIsNull_thenError() {
		testInvalidAttributes(UserTestType.NULL_USER);
	}
	
	@Test
	public void whenUserIsUpdated_thenChangesAreSaved() throws ForumException {
		User testUser = TestObjects.getTestUser();
		User savedUser = userService.addUser(testUser);
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getNickname()).isEqualTo(testUser.getNickname());
		assertThat(savedUser.getEmail()).isEqualTo(testUser.getEmail());
		
		savedUser.setNickname("test_updated");
		savedUser.setEmail("updated@vanhack.com");
		
		User updatedUser = userService.updateUser(savedUser);
		
		assertThat(updatedUser).isNotNull();
		assertThat(updatedUser.getId()).isEqualTo(savedUser.getId());
		assertThat(updatedUser.getNickname()).isEqualTo(savedUser.getNickname());
		assertThat(updatedUser.getEmail()).isEqualTo(savedUser.getEmail());
	}
	
	@Test
	public void whenUserIsDeleted_thenUserIsRemovedFromDB() throws ForumException {
		User testUser = TestObjects.getTestUser();
		User savedUser = userService.addUser(testUser);
		long savedUserId = savedUser.getId();
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getNickname()).isEqualTo(testUser.getNickname());
		assertThat(savedUser.getEmail()).isEqualTo(testUser.getEmail());
		
		userService.deleteUser(savedUserId);
		assertThat(userService.findById(savedUserId)).isNull();
	}
	
	private void testInvalidAttributes(UserTestType testType) {	
		User testUser = TestObjects.getTestUser();
		User newUser = null;
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
			case NULL_USER:
				testUser = null;
				exceptionCode = UserCodes.USER_NULL_CODE;
				exceptionMessage = UserCodes.USER_NULL_MESSAGE;
				break;
			default:
				break;
		}
		
		try {
			newUser = userService.addUser(testUser);
		} catch(ForumException thrown) {
			assertThat(thrown).isInstanceOf(UserException.class);
			assertThat(thrown.getCode()).isEqualTo(exceptionCode);
			assertThat(thrown.getMessage()).isEqualTo(exceptionMessage);
			assertThat(newUser).isNull();
		}
		
		if(testType == UserTestType.USER_OK) {
			assertThat(newUser).isNotNull();
			assertThat(newUser.getId()).isNotNull();
			assertThat(newUser.getNickname()).isEqualTo(testUser.getNickname());
			assertThat(newUser.getEmail()).isEqualTo(testUser.getEmail());
		}
		
	}
}
