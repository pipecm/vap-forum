package com.vanhack.forum.restapi.user;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.restapi.AppRequest;

public class AddUserRequest extends AppRequest {
	
	private User newUser;

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
}
