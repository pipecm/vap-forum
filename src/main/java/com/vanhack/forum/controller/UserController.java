package com.vanhack.forum.controller;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;
import com.vanhack.forum.util.UserErrorCodes;

@Controller
public class UserController {
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	private UserDAO userDao;
	private UserErrorCodes userErrorCodes;
	
	@Autowired
	public UserController(UserDAO userDao, UserErrorCodes userErrorCodes) {
		this.userDao = userDao;
		this.userErrorCodes = userErrorCodes;
	}
	
	public int addUser(User user) throws UserException {
		if(validateUser(user)) {
			try {
				return userDao.addUser(user);
			} catch(Exception e) {
				UserException ue = new UserException(userErrorCodes.USER_UNEXPECTED_ERROR_CODE, userErrorCodes.USER_UNEXPECTED_ERROR_MESSAGE);
				ue.initCause(e);
				throw ue;
			}
		}
		return 1;
	}
	
	public Iterable<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	public User findById(Long id) {
		return userDao.findById(id);
	}

	public User findByNickname(String nickname) {
		return userDao.findByNickname(nickname);
	}

	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	public int updateUser(User user) throws UserException {
		if(validateUser(user)) {
			try {
				return userDao.updateUser(user);
			} catch(Exception e) {
				UserException ue = new UserException(userErrorCodes.USER_UNEXPECTED_ERROR_CODE, userErrorCodes.USER_UNEXPECTED_ERROR_MESSAGE);
				ue.initCause(e);
				throw ue;
			}
		}
		return 1;
	}
	
	public int deleteUser(Long id) throws UserException {
		return userDao.deleteUser(id);
	}
	
	private boolean validateUser(User user) throws UserException {
		if(user.getNickname() == null || user.getNickname().equals("")) {
			throw new UserException(userErrorCodes.USER_EMPTY_NICKNAME_CODE, userErrorCodes.USER_EMPTY_NICKNAME_MESSAGE);
		} else if(user.getEmail() == null || user.getEmail().equals("")) {
			throw new UserException(userErrorCodes.USER_EMPTY_EMAIL_CODE, userErrorCodes.USER_EMPTY_EMAIL_MESSAGE);
		} else if(user.getPassword() == null || user.getPassword().equals("")) {
			throw new UserException(userErrorCodes.USER_EMPTY_PASSWORD_CODE, userErrorCodes.USER_EMPTY_PASSWORD_MESSAGE);
		} else if(!validateEmail(user.getEmail())) {
			throw new UserException(userErrorCodes.USER_INVALID_EMAIL_CODE, userErrorCodes.USER_INVALID_EMAIL_MESSAGE);
		} else if(userDao.findByNickname(user.getNickname()) != null) {
			throw new UserException(userErrorCodes.USER_NICKNAME_ALREADY_EXISTS_CODE, userErrorCodes.USER_NICKNAME_ALREADY_EXISTS_MESSAGE);
		} else if (userDao.findByEmail(user.getEmail()) != null) {
			throw new UserException(userErrorCodes.USER_EMAIL_ALREADY_EXISTS_CODE, userErrorCodes.USER_EMAIL_ALREADY_EXISTS_MESSAGE);
		}
		return true;
	}
	
	private boolean validateEmail(String email) {
		boolean validEmail = true;
		try {
			InternetAddress address = new InternetAddress(email);
			address.validate();
		} catch (AddressException e) {
			validEmail = false;
		}
		return validEmail;
	}

}
