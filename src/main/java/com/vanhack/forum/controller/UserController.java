package com.vanhack.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;
import com.vanhack.forum.util.Codes;
import com.vanhack.forum.util.Messages;

@Controller
public class UserController {
	
	private UserDAO userDao;
	
	@Autowired
	public UserController(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public int addUser(User user) throws UserException {
		if(user.getNickname() == null || user.getNickname().equals("")) {
			throw new UserException(Codes.USER_NULL_NICKNAME, Messages.USER_NULL_NICKNAME);
		} else if(user.getEmail() == null || user.getEmail().equals("")) {
			throw new UserException(Codes.USER_NULL_EMAIL, Messages.USER_NULL_EMAIL);
		} else if(userDao.findByNickname(user.getNickname()) != null) {
			
		} else if (userDao.findByEmail(user.getEmail()) != null) {
			
		} 
		
		return userDao.addUser(user);
	}

}
