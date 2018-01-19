package com.vanhack.forum.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserAlreadyExistsException;
import com.vanhack.forum.exception.UserException;
import com.vanhack.forum.repo.UserRepository;
import com.vanhack.forum.util.Messages;

@Repository
public class UserDAO {
	
	private UserRepository repo;
	
	@Autowired
	public UserDAO(UserRepository repo) {
		this.repo = repo;
	}

	public Iterable<User> getAllUsers() {
		return repo.findAll();
	}

	public int addUser(User user) throws UserException {
		try {
			repo.save(user);
		} catch(Exception e) {
			throw new UserException(1, e.getMessage());
		}
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
	
	public void updateUser(User user) throws UserAlreadyExistsException {
		if(this.findByNickname(user.getNickname()) != null) {
			throw new UserAlreadyExistsException(Messages.USER_NICKNAME_ALREADY_EXISTS);
		} else if (this.findByEmail(user.getEmail()) != null) {
			throw new UserAlreadyExistsException(Messages.USER_EMAIL_ALREADY_EXISTS);
		} else {
			repo.save(user);
		}	
	}
	
	public void deleteUser(Long id) {
		User user = repo.findOne(id);
		repo.delete(user);
	}
	
}
