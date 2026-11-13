package com.nagarro.training.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String username;
	
	private String password;

	public User() {
		super();
	}

	public User(String username, String passsword) {
		super();
		this.username = username;
		this.password = passsword;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", passsword=" + password + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasssword() {
		return password;
	}

	public void setPasssword(String passsword) {
		this.password = passsword;
	}
	
	
}
