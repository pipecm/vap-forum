package com.vanhack.forum.controller;

import java.text.MessageFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.forum.controller.ForumResponseFactory.ResponseType;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.service.UserService;
import com.vanhack.forum.util.ForumConstants;
import com.vanhack.forum.util.ForumConstants.UserConstants;
import com.vanhack.forum.util.UserCodes;

@RestController
@RequestMapping(ForumConstants.API_ENDPOINT)
public class UserController {
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = UserConstants.USER_GET_ALL_ENDPOINT)
	public @ResponseBody ResponseEntity<ForumResponse> listAllUsers() {
		ForumResponse response = null;
		log.info("Fetching all users");
		List<User> users = userService.getAllUsers();
		if(users.isEmpty()) {
			log.info("No users found");
			response = ForumResponseFactory.create(ResponseType.USER_RESPONSE, 
													UserCodes.USER_NO_USERS_FOUND_CODE, 
													UserCodes.USER_NO_USERS_FOUND_MESSAGE);
			return new ResponseEntity<ForumResponse>(response, HttpStatus.NO_CONTENT); 
		}
		log.info(users.size() + " users found");
		response = ForumResponseFactory.create(ResponseType.USER_RESPONSE, 
												UserCodes.USER_SUCCESS_CODE, 
												MessageFormat.format(UserCodes.USER_FIND_ALL_SUCCESS_MESSAGE, users.size()));
		response.setResponseContent(users);
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}

	@GetMapping(path = "/user/{id}")
	public @ResponseBody ResponseEntity<?> getUser(@PathVariable("id") Long id) {
		log.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if(user == null) {
			log.error("User with id {} not found", id);
			return new ResponseEntity<String>("User with id " + id 
                    + " not found", HttpStatus.NOT_FOUND);	
		}
		log.info("User found: " + user.toString());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping(path = "/user/find")
	public @ResponseBody ResponseEntity<ForumResponse> findUsersByNickname(@RequestParam("nickname") String nickname) {
		ForumResponse response = null;
		log.info("Fetching User with nickname containing {}", nickname);
		List<User> users = userService.findByNicknameContaining(nickname);
		if(users.isEmpty() || users == null) {
			response = ForumResponseFactory.create(ResponseType.USER_RESPONSE, 
													UserCodes.USER_NICKNAME_NOT_FOUND_CODE, 
													MessageFormat.format(UserCodes.USER_NICKNAME_NOT_FOUND_MESSAGE, nickname));
			log.debug(response);
			return new ResponseEntity<ForumResponse>(response, HttpStatus.NOT_FOUND);	
		}
		response = ForumResponseFactory.create(ResponseType.USER_RESPONSE, 
												UserCodes.USER_SUCCESS_CODE, 
												MessageFormat.format(UserCodes.USER_FIND_BY_NICKNAME_SUCCESS_MESSAGE, users.size(), nickname));
		response.setResponseContent(users);
		log.debug(response);
		return new ResponseEntity<ForumResponse>(response, HttpStatus.OK);
	}
	
	
}
