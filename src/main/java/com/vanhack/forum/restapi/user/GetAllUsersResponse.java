package com.vanhack.forum.restapi.user;

import com.vanhack.forum.dto.User;
import com.vanhack.forum.restapi.AppResponse;

public class GetAllUsersResponse extends AppResponse {

	private Iterable<User> usersList;

	public Iterable<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(Iterable<User> usersList) {
		this.usersList = usersList;
	}
	
}
