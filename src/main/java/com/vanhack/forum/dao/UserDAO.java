package com.vanhack.forum.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;
import com.vanhack.forum.repo.UserRepository;

@Repository
public class UserDAO {
	
	private static final Logger log = LogManager.getLogger(UserDAO.class);
	
	private UserRepository repo;
	
	@Autowired
	public UserDAO(UserRepository repo) {
		this.repo = repo;
	}

	public Iterable<User> getAllUsers() {
		return repo.findAll();
	}

	public int addUser(User user) throws Exception {
		repo.save(user);
		return 0;
	}
	
	public User findById(Long id) {
		return repo.findOne(id);
	}
	
	public User findByNickname(String nickname) {
		return repo.findByNickname(nickname);
	}
	
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	public int updateUser(User user) throws UserException {
		try {
			repo.save(user);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new UserException(1, e.getMessage());
		}
		return 0;
	}
	
	public int deleteUser(Long id) throws UserException {
		try {
			User user = repo.findOne(id);
			repo.delete(user);
		} catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new UserException(1, e.getMessage());
		}
		return 0;
	}
	
	public boolean isNicknameAvailable(User user) {;
		if(repo.checkNickname(user.getId(), user.getNickname()) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmailAvailable(User user) {
		if(repo.checkEmail(user.getId(), user.getEmail()) == null) {
			return true;
		} else {
			return false;
		}
	}
	
}
