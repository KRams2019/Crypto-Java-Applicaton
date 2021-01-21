package com.example.demo.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;


import com.example.demo.apiResponse.ApiResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.EncryptUserService;

/**
* Represents the Rest controller
* @author M1056279
* @version 1.0
* 
*/
@RestController("/encryption")
public class MyApplicationController {
	
	public final static Logger log = Logger.getLogger(MyApplicationController.class.getName());

	@Autowired
	EncryptUserService encryptUserService;

	/**
	 * Add user.
	 * @param userDto Takes UserDto Object Parameter to add User
	 * @return the response entity
	 *@throws ServiceException 
	 */
	@PostMapping("/adduser")
	public ResponseEntity<ApiResponse> addUser(@RequestBody UserDto userDto) throws ServiceException {

		log.info("Inside Controller Class and method of Add User called");
		UserDto savedUserDto = encryptUserService.addUsers(userDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Adding User", false, savedUserDto, HttpStatus.OK),
				HttpStatus.OK);
	}

	/**
	 * Decode User.
	 * @param userDto Takes UserDto Object Parameter to decreypt User
	 * @return the response entity
	 * @throws ServiceException 
	 */
	@PostMapping("/decode")
	public ResponseEntity<ApiResponse> decryptUser(@RequestBody UserDto userDto) throws ServiceException {
		log.info("Inside Controller Class and method of Decode User called");
		UserDto decodeUser = encryptUserService.decryptUser(userDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Decode User", false, decodeUser, HttpStatus.OK),
				HttpStatus.OK);
	}

	/**
	 * Encode User.
	 * @param userDto Takes UserDto Object Parameter to Encode the User
	 * @return the response entity
	 * @throws ServiceException 
	 */
	@PostMapping("/encode")
	public ResponseEntity<ApiResponse> encodeUser(@RequestBody UserDto userDto) throws ServiceException {

		log.info("Inside Controller Class and method of Encode User called");
		UserDto encodeUser = encryptUserService.encrypytUser(userDto);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Encode User", false, encodeUser, HttpStatus.OK),
				HttpStatus.OK);
	}

	/**
	 * Search User.
	 * @param name search for that keywords
	 * @return the response entity
	 */
	@PostMapping("/search/{name}")
	public ResponseEntity<ApiResponse> findUser(@PathVariable String name) {

		log.info("Inside Controller Class and method of Search User by keywords called");
		List<UserDto> encodeUser = encryptUserService.findUser(name);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Encode User", false, encodeUser, HttpStatus.OK),
				HttpStatus.OK);
	}
		
}
