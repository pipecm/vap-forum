package com.vanhack.forum;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
		
	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private UserDAO userDao;
	
	@Test
	public void whenTableIsEmpty_noUsersFound() {
		List<User> users = userDao.findAll();
		assertThat(users).isEmpty();
	}
	
	@Test
	public void whenTableIsNotEmpty_thenReturnAllUsers() {
		User firstUser = TestObjects.getTestUser();
		User secondUser = TestObjects.getTestUser();
		firstUser.setNickname("first_user");
		secondUser.setNickname("second_user");
		entityManager.persist(firstUser);
		entityManager.persist(secondUser);
		entityManager.flush();
		
		List<User> users = userDao.findAll();
		
		assertThat(users).isNotEmpty();
		assertThat(users.size()).isEqualTo(2);
	}
	
	@Test
	public void whenUserIsSaved_itMustBeRecordedInDB() {
		User testUser = TestObjects.getTestUser();
		User savedUser = userDao.save(testUser);
		
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser).hasFieldOrPropertyWithValue("nickname", "test_user");
		assertThat(savedUser).hasFieldOrPropertyWithValue("email", "test@vanhack.com");		
	}
	
	@Test
	public void whenUserIsUpdated_itMustBeRecordedInDB() {
		User testUser = TestObjects.getTestUser();
		User savedUser = userDao.save(testUser);
		
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser).hasFieldOrPropertyWithValue("nickname", "test_user");
		assertThat(savedUser).hasFieldOrPropertyWithValue("email", "test@vanhack.com");	
		
		savedUser.setNickname("test_updated");
		savedUser.setEmail("updated@vanhack.com");
		
		User updatedUser = userDao.save(savedUser);
		assertThat(updatedUser.getId()).isEqualTo(savedUser.getId());
		assertThat(updatedUser).hasFieldOrPropertyWithValue("nickname", "test_updated");
		assertThat(updatedUser).hasFieldOrPropertyWithValue("email", "updated@vanhack.com");	
	}
	
	@Test
	public void whenUserIsDeleted_itMustBeDeletedFromDB() {
		User testUser = TestObjects.getTestUser();
		User savedUser = userDao.save(testUser);
		
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser).hasFieldOrPropertyWithValue("nickname", "test_user");
		assertThat(savedUser).hasFieldOrPropertyWithValue("email", "test@vanhack.com");	
		
		long deletedUserId = savedUser.getId();
		userDao.delete(deletedUserId);
		assertThat(userDao.findById(deletedUserId)).isNull();
	}
	
	@Test
	public void whenFindByNickname_thenReturnUserList() {
		String keyword = "test";
		User firstUser = TestObjects.getTestUser();
		User secondUser = TestObjects.getTestUser();
		firstUser.setNickname("test_one");
		secondUser.setNickname("test_two");
		entityManager.persist(firstUser);
		entityManager.persist(secondUser);
		entityManager.flush();

		List<User> usersFound = userDao.findByNicknameContaining(keyword);
		
		assertThat(usersFound).isNotEmpty();
		for(User found : usersFound) {
			assertThat(found.getNickname()).contains(keyword);
		}
	}
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		User testUser = TestObjects.getTestUser();
		entityManager.persist(testUser);
		entityManager.flush();
	
		User found = userDao.findByEmail(testUser.getEmail());
		assertThat(found.getEmail()).isEqualTo(testUser.getEmail());	
	}
}
