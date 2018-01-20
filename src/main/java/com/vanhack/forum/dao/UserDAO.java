package com.vanhack.forum.dao;

import java.util.List;

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
		User user = null;
		List<User> list = repo.findByNickname(nickname);
		for(User userByNickname : list) {
			user = userByNickname;
		}
		return user;
	}
	
	public User findByEmail(String email) {
		User user = null;
		List<User> list = repo.findByEmail(email);
		for(User userByEmail : list) {
			user = userByEmail;
		}
		return user;
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
	
	public boolean isNicknameAvailable(User user) {
		List<User> list = repo.checkNickname(user.getId(), user.getNickname());
		if(list.isEmpty() || list == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmailAvailable(User user) {
		List<User> list = repo.checkEmail(user.getId(), user.getEmail());
		if(list.isEmpty() || list == null) {
			return true;
		} else {
			return false;
		}
	}
	
}
