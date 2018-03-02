package com.vanhack.forum.service;

import java.util.List;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
	
	private Validator validator;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserService() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
	}
	
	public User addUser(User user) throws ForumException {
		User savedUser = null;
		if(validateUser(user)) {
			try {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				savedUser = userDao.save(user);
			} catch(Exception cause) {
				throwUserException(UserCodes.USER_UNEXPECTED_ERROR_CODE, 
									UserCodes.USER_UNEXPECTED_ERROR_MESSAGE,
									cause);
			}
		}
		return savedUser;
	}
	
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	public User findById(Long id) {
		return userDao.findById(id);
	}

	public User findByEmail(String email) throws ForumException {
		if(!validateEmail(email)) {
			throwUserException(UserCodes.USER_INVALID_EMAIL_CODE, 
								UserCodes.USER_INVALID_EMAIL_MESSAGE);
		}
		return userDao.findByEmail(email);
	}
	
	public List<User> findByNicknameContaining(String nickname) {
		return userDao.findByNicknameContaining(nickname);
	}

	public User updateUser(User user) throws ForumException {
		User savedUser = null;
		if(validateUser(user)) {
			try {
				savedUser = userDao.save(user);
			} catch(Exception cause) {
				throwUserException(UserCodes.USER_UNEXPECTED_ERROR_CODE, 
									UserCodes.USER_UNEXPECTED_ERROR_MESSAGE,
									cause);
			}
		}
		return savedUser;
	}
	
	public int deleteUser(Long id) throws ForumException {
		try {
			userDao.delete(id);
		} catch(Exception cause) {
			throwUserException(UserCodes.USER_UNEXPECTED_ERROR_CODE, 
								UserCodes.USER_UNEXPECTED_ERROR_MESSAGE,
								cause);
		}
		return UserCodes.USER_SUCCESS_CODE;
	}
	
	private boolean validateUser(User user) throws ForumException {
		if(user.getNickname() == null || user.getNickname().equals("")) {
			throwUserException(UserCodes.USER_EMPTY_NICKNAME_CODE, 
								UserCodes.USER_EMPTY_NICKNAME_MESSAGE);
		} else if(user.getEmail() == null || user.getEmail().equals("")) {
			throwUserException(UserCodes.USER_EMPTY_EMAIL_CODE, 
								UserCodes.USER_EMPTY_EMAIL_MESSAGE);
		} else if(user.getPassword() == null || user.getPassword().equals("")) {
			throwUserException(UserCodes.USER_EMPTY_PASSWORD_CODE, 
								UserCodes.USER_EMPTY_PASSWORD_MESSAGE);
		} else if(!validateEmail(user.getEmail())) {
			throwUserException(UserCodes.USER_INVALID_EMAIL_CODE, 
								UserCodes.USER_INVALID_EMAIL_MESSAGE);
		} else if(!isNicknameAvailable(user)) {
			throwUserException(UserCodes.USER_NICKNAME_ALREADY_EXISTS_CODE, 
								UserCodes.USER_NICKNAME_ALREADY_EXISTS_MESSAGE);
		} else if (!isEmailAvailable(user)) {
			throwUserException(UserCodes.USER_EMAIL_ALREADY_EXISTS_CODE, 
								UserCodes.USER_EMAIL_ALREADY_EXISTS_MESSAGE);
		} else {
			Set<ConstraintViolation<User>> violations = validator.validate(user);
			if(violations != null && !violations.isEmpty()) {
				throwUserException(UserCodes.USER_VALIDATION_ERROR_CODE,
									UserCodes.USER_VALIDATION_ERROR_MESSAGE
									+ ": " + getValidationMessages(violations));
			}
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
	
	private String getValidationMessages(Set<ConstraintViolation<User>> violations) {
		StringBuilder builder = new StringBuilder();
		for(ConstraintViolation<User> violation : violations) {
    		builder.append(violation.getMessage());
    		builder.append("; ");
    	}
		return new String(builder);
	}

}
