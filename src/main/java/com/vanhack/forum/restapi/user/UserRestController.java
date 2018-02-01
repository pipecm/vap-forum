package com.vanhack.forum.restapi.user;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.exception.ForumException;
import com.vanhack.forum.service.UserService;

@RestController  
@RequestMapping("/api/users") 
public class UserRestController {
	
	private static final Logger log = LogManager.getLogger(UserRestController.class);

	@Autowired            
	private UserService userService;
	
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
			response.setResponseCode(userService.addUser(request.getNewUser()));
			response.setResponseMessage("OK");
			response.setNewUser(userService.findByNickname(request.getNewUser().getNickname()));
		} catch(ForumException e) {
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
		Iterable<User> usersList = userService.getAllUsers();
		GetAllUsersResponse response = new GetAllUsersResponse();
		response.setResponseCode(0);
		response.setResponseMessage("OK");
		response.setUsersList(usersList);
		return response; // new ResponseEntity<GetAllUsersResponse>(response, HttpStatus.OK);
	}
}
