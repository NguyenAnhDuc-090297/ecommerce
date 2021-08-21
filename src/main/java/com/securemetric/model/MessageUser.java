package com.securemetric.model;

import java.util.ArrayList;
import java.util.List;

import com.securemetric.entity.User;

public class MessageUser {

	private String message = "";
	private List<User> users = new ArrayList<User>();
	private String error;
	
	public MessageUser(String message, List<User> users, String error) {
		this.message = message;
		this.users = users;
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
