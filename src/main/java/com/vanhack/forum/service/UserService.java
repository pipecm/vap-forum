package com.vanhack.forum.service;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dao.UserDAO;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.exception.ForumExceptionFactory;
import com.vanhack.forum.exception.ForumExceptionFactory.ExceptionType;
import com.vanhack.forum.util.UserCodes;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private UserCodes userCodes;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public int addUser(User user) throws ForumException {
		if(validateUser(user)) {
			try {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				userDao.save(user);
			} catch(Exception cause) {
				throwUserException(userCodes.USER_UNEXPECTED_ERROR_CODE, 
									userCodes.USER_UNEXPECTED_ERROR_MESSAGE,
									cause);
			}
		}
		return userCodes.USER_SUCCESS_CODE;
	}
	
	public List<User> getAllUsers() {
		return userDao.findAll();
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

	public int updateUser(User user) throws ForumException {
		if(validateUser(user)) {
			try {
				userDao.save(user);
			} catch(Exception cause) {
				throwUserException(userCodes.USER_UNEXPECTED_ERROR_CODE, 
									userCodes.USER_UNEXPECTED_ERROR_MESSAGE,
									cause);
			}
		}
		return userCodes.USER_SUCCESS_CODE;
	}
	
	public int deleteUser(Long id) throws ForumException {
		try {
			userDao.delete(id);
		} catch(Exception cause) {
			throwUserException(userCodes.USER_UNEXPECTED_ERROR_CODE, 
								userCodes.USER_UNEXPECTED_ERROR_MESSAGE,
								cause);
		}
		return userCodes.USER_SUCCESS_CODE;
	}
	
	private boolean validateUser(User user) throws ForumException {
		if(user.getNickname() == null || user.getNickname().equals("")) {
			throwUserException(userCodes.USER_EMPTY_NICKNAME_CODE, 
								userCodes.USER_EMPTY_NICKNAME_MESSAGE);
		} else if(user.getEmail() == null || user.getEmail().equals("")) {
			throwUserException(userCodes.USER_EMPTY_EMAIL_CODE, 
								userCodes.USER_EMPTY_EMAIL_MESSAGE);
		} else if(user.getPassword() == null || user.getPassword().equals("")) {
			throwUserException(userCodes.USER_EMPTY_PASSWORD_CODE, 
								userCodes.USER_EMPTY_PASSWORD_MESSAGE);
		} else if(!validateEmail(user.getEmail())) {
			throwUserException(userCodes.USER_INVALID_EMAIL_CODE, 
								userCodes.USER_INVALID_EMAIL_MESSAGE);
		} else if(!isNicknameAvailable(user)) {
			throwUserException(userCodes.USER_NICKNAME_ALREADY_EXISTS_CODE, 
								userCodes.USER_NICKNAME_ALREADY_EXISTS_MESSAGE);
		} else if (!isEmailAvailable(user)) {
			throwUserException(userCodes.USER_EMAIL_ALREADY_EXISTS_CODE, 
								userCodes.USER_EMAIL_ALREADY_EXISTS_MESSAGE);
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
	
	public boolean isNicknameAvailable(User user) {;
		if(userDao.checkNickname(user.getId(), user.getNickname()) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmailAvailable(User user) {
		if(userDao.checkEmail(user.getId(), user.getEmail()) == null) {
			return true;
		} else {
			return false;
		}
	}

	private void throwUserException(int code, String message) throws ForumException {
		throw ForumExceptionFactory.create(ExceptionType.USER_EXCEPTION, code, message);
	}
	
	private void throwUserException(int code, String message, Throwable cause) throws ForumException {
		ForumException exception = ForumExceptionFactory.create(ExceptionType.USER_EXCEPTION, code, message);
		exception.initCause(cause);
		throw exception;
	}
}
