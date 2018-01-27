package com.vanhack.forum.service;

import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;
import com.vanhack.forum.repo.UserRepository;
import com.vanhack.forum.util.UserCodes;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCodes userCodes;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public int addUser(User user) throws UserException {
		if(validateUser(user)) {
			try {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				userRepository.save(user);
			} catch(Exception e) {
				UserException ue = new UserException(userCodes.USER_UNEXPECTED_ERROR_CODE, userCodes.USER_UNEXPECTED_ERROR_MESSAGE);
				ue.initCause(e);
				throw ue;
			}
		}
		return userCodes.USER_SUCCESS_CODE;
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		return userRepository.findById(id);
	}

	public User findByNickname(String nickname) {
		return userRepository.findByNickname(nickname);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public int updateUser(User user) throws UserException {
		if(validateUser(user)) {
			try {
				userRepository.save(user);
			} catch(Exception e) {
				UserException ue = new UserException(userCodes.USER_UNEXPECTED_ERROR_CODE, userCodes.USER_UNEXPECTED_ERROR_MESSAGE);
				ue.initCause(e);
				throw ue;
			}
		}
		return userCodes.USER_SUCCESS_CODE;
	}
	
	public int deleteUser(Long id) throws UserException {
		try {
			userRepository.delete(id);
		} catch(Exception e) {
			UserException ue = new UserException(userCodes.USER_UNEXPECTED_ERROR_CODE, userCodes.USER_UNEXPECTED_ERROR_MESSAGE);
			ue.initCause(e);
			throw ue;
		}
		return userCodes.USER_SUCCESS_CODE;
	}
	
	private boolean validateUser(User user) throws UserException {
		if(user.getNickname() == null || user.getNickname().equals("")) {
			throw new UserException(userCodes.USER_EMPTY_NICKNAME_CODE, userCodes.USER_EMPTY_NICKNAME_MESSAGE);
		} else if(user.getEmail() == null || user.getEmail().equals("")) {
			throw new UserException(userCodes.USER_EMPTY_EMAIL_CODE, userCodes.USER_EMPTY_EMAIL_MESSAGE);
		} else if(user.getPassword() == null || user.getPassword().equals("")) {
			throw new UserException(userCodes.USER_EMPTY_PASSWORD_CODE, userCodes.USER_EMPTY_PASSWORD_MESSAGE);
		} else if(!validateEmail(user.getEmail())) {
			throw new UserException(userCodes.USER_INVALID_EMAIL_CODE, userCodes.USER_INVALID_EMAIL_MESSAGE);
		} else if(userRepository.findByNickname(user.getNickname()) != null) {
			throw new UserException(userCodes.USER_NICKNAME_ALREADY_EXISTS_CODE, userCodes.USER_NICKNAME_ALREADY_EXISTS_MESSAGE);
		} else if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserException(userCodes.USER_EMAIL_ALREADY_EXISTS_CODE, userCodes.USER_EMAIL_ALREADY_EXISTS_MESSAGE);
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
		if(userRepository.checkNickname(user.getId(), user.getNickname()) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmailAvailable(User user) {
		if(userRepository.checkEmail(user.getId(), user.getEmail()) == null) {
			return true;
		} else {
			return false;
		}
	}

}
