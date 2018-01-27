package com.vanhack.forum.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/user")
	public ResponseEntity<List<User>> listAllUsers() {
		log.info("Fetching all users");
		List<User> users = userService.getAllUsers();
		if(users.isEmpty()) {
			log.info("No users found");
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT); 
		}
		log.info(users.size() + " users found");
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@GetMapping(path = "/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
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
	
	
}
