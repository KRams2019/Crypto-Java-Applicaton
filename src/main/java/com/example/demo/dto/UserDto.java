package com.example.demo.dto;

/**
* This class defines the User DTO.
* @author M1056279
* @version 1.0
*
*/
public class UserDto {

	String userName;
	String password;

	public UserDto() {
		super();
	}

	public UserDto(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
