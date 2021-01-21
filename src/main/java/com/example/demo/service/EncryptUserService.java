package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.ServiceException;

public interface EncryptUserService {

	
	public UserDto addUsers(UserDto userDto) throws ServiceException;
	
	public UserDto encrypytUser(UserDto userDto) throws ServiceException;
	
	public UserDto decryptUser(UserDto userDto) throws ServiceException;
	
	public List<UserDto> findUser(String searchUser);
}
