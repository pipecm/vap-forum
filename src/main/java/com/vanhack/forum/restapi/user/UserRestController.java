package com.vanhack.forum.restapi.user;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.forum.controller.UserController;
import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.UserException;

@RestController  
@RequestMapping("/api/users") 
public class UserRestController {
	
	private static final Logger log = LogManager.getLogger(UserRestController.class);

	@Autowired            
	private UserController userController;
	
	/*
	@PostMapping(path="/addUser") 
	public @ResponseBody ResponseEntity<User> addNewUser(@RequestBody User user) throws UserException {
		userController.addUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	} */
	
	@PostMapping(path="/addUser")
	public @ResponseBody AddUserResponse addUser(@Valid @RequestBody AddUserRequest request) {
		AddUserResponse response = new AddUserResponse();
		try {
			response.setResponseCode(userController.addUser(request.getNewUser()));
			response.setResponseMessage("OK");
			response.setNewUser(userController.findByNickname(request.getNewUser().getNickname()));
		} catch(UserException e) {
			log.error(e.getMessage(), e);
			response.setResponseCode(e.getCode());
			response.setResponseMessage(e.getMessage());
		}
		return response;
	}
	
	/*
	@GetMapping(path="/allUsers")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userController.getAllUsers();
	} */
	
	@PostMapping(path="/allUsers")
	public @ResponseBody GetAllUsersResponse getAllUsers(@RequestBody GetAllUsersRequest request) {
		Iterable<User> usersList = userController.getAllUsers();
		GetAllUsersResponse response = new GetAllUsersResponse();
		response.setResponseCode(0);
		response.setResponseMessage("OK");
		response.setUsersList(usersList);
		return response; // new ResponseEntity<GetAllUsersResponse>(response, HttpStatus.OK);
	}
}
