package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDataTests {
	
	private static final int FIND_BY_NICKNAME 	= 1;
	private static final int FIND_BY_EMAIL		= 2;
	
	private static final int USER_OK		 	= 0;
	private static final int EMPTY_NICKNAME 	= 1;
	private static final int EMPTY_EMAIL 		= 2;
	private static final int EMPTY_PASSWORD 	= 3;
	private static final int SHORTER_NICKNAME 	= 4;
	private static final int SHORTER_PASSWORD 	= 5;
	private static final int LONGER_NICKNAME 	= 6;
	private static final int INVALID_EMAIL 		= 7;
	
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private UserDAO userDao;
	
	private static Validator validator;
	
	private static final Logger log = LogManager.getLogger(UserDataTests.class);
	
	@Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
	public void whenTableIsEmpty_noUsersFound() {
		List<User> users = userDao.findAll();
		assertThat(users).isEmpty();
	}
	
	@Test
	public void whenUserIsSaved_itMustBeRecordedInDB() {
		User testUser = getTestUser();
		userDao.save(testUser);
		
		assertThat(testUser).hasFieldOrPropertyWithValue("nickname", "test_user");
		assertThat(testUser).hasFieldOrPropertyWithValue("email", "test@vanhack.com");		
	}
	
	@Test
	public void whenFindByNickname_thenReturnUser() {
		testFindUser(FIND_BY_NICKNAME);
	}
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		testFindUser(FIND_BY_EMAIL);
	}
	
	@Test
	public void whenAllFieldsAreCorrect_thenOk() {
		testInvalidAttributes(USER_OK);
	}
	
	@Test
	public void whenNicknameIsEmpty_thenError() {
		testInvalidAttributes(EMPTY_NICKNAME);
	}
	
	@Test
	public void whenEmailIsEmpty_thenError() {
		testInvalidAttributes(EMPTY_EMAIL);
	}
	
	@Test
	public void whenPasswordIsEmpty_thenError() {
		testInvalidAttributes(EMPTY_PASSWORD);
	}
	
	@Test
	public void whenNicknameHasLessThan5Characters_thenError() {
		testInvalidAttributes(SHORTER_NICKNAME);
	}
	
	@Test
	public void whenPasswordHasLessThan5Characters_thenError() {
		testInvalidAttributes(SHORTER_PASSWORD);
	}
	
	@Test
	public void whenNicknameHasMoreThan20Characters_thenError() {
		testInvalidAttributes(LONGER_NICKNAME);
	}
	
	@Test
	public void whenEmailIsInvalid_thenError() {
		testInvalidAttributes(INVALID_EMAIL);
	}
	
	private User getTestUser() {
		User testUser = new User();
		testUser.setNickname("test_user");
		testUser.setEmail("test@vanhack.com");
		testUser.setPassword("testuser");
		return testUser;
	}
	
	private void testFindUser(int attribute) {
		User testUser = getTestUser();
		User found = null;
		entityManager.persist(testUser);
		entityManager.flush();
		
		switch(attribute) {
			case FIND_BY_NICKNAME:
				found = userDao.findByNickname(testUser.getNickname());
				assertThat(found.getNickname()).isEqualTo(testUser.getNickname());
				break;
			case FIND_BY_EMAIL:
				found = userDao.findByEmail(testUser.getEmail());
				assertThat(found.getEmail()).isEqualTo(testUser.getEmail());
				break;
			default:
				break;
		}
	}
	
	private void testInvalidAttributes(int attribute) {
		User testUser = getTestUser();
		
		switch(attribute) {
			case EMPTY_NICKNAME:
				testUser.setNickname("");
				break;
			case EMPTY_EMAIL:
				testUser.setEmail("");
				break;
			case EMPTY_PASSWORD:
				testUser.setPassword("");
				break;
			case SHORTER_NICKNAME:
				testUser.setNickname("test");
				break;
			case SHORTER_PASSWORD:
				testUser.setPassword("123");
				break;
			case LONGER_NICKNAME:
				testUser.setNickname("test_test_test_test_test");
				break;
			case INVALID_EMAIL:
				testUser.setEmail("test.test.com");
				break;
			default:
				break;
		}
		
		Set<ConstraintViolation<User>> violations = validator.validate(testUser);
		for(ConstraintViolation<User> violation : violations) {
    		log.info(violation.getMessage());
    	}
		
		if(attribute == USER_OK) {
			assertThat(violations).isEmpty();
		} else {
			assertThat(violations).isNotEmpty();
		}
	}
}
