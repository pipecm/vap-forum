package com.vanhack.forum.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserAlreadyExistsException;
import com.vanhack.forum.repo.UserRepository;

public class UserDAO {
	
	@Autowired
	private UserRepository repo;

	public Iterable<User> getAllUsers() {
		return repo.findAll();
	}

	public void addUser(User user) throws UserAlreadyExistsException {
		if(this.findByNickname(user.getNickname()) != null) {
			throw new UserAlreadyExistsException("Nickname already exists!");
		} else if (this.findByEmail(user.getEmail()) != null) {
			throw new UserAlreadyExistsException("Email already exists!");
		} else {
			repo.save(user);
		}
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
	
	public void updateUser(User user) {
		repo.save(user);	
	}
	
	public void deleteUser(Long id) {
		User user = repo.findOne(id);
		repo.delete(user);
	}
	
}
