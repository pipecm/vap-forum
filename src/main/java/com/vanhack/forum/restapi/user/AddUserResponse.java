package com.vanhack.forum.restapi.user;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.restapi.AppResponse;

public class AddUserResponse extends AppResponse {
	
	private User newUser;

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
}
