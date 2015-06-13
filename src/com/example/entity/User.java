package com.example.entity;

public class User extends SuperEntity {
	private String username;
	private String password;
	private String email;
	private String phone;
	
	public User() {
		super();
	}

	public User(Integer id, String username, String password, String email, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
