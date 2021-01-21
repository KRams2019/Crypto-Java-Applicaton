package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	

	private static String UPLOADED_FOLDER = "C://Users//M1056279//Desktop//";
	private final Path root = Paths.get(UPLOADED_FOLDER);
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
	
	
	 @PostMapping("/upload") // //new annotation since 4.3
	    public String singleFileUpload(@RequestParam("file") MultipartFile file) {

	        if (file.isEmpty()) {
	           System.out.println("File Empty");
	        }

	        try {

	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            System.out.println(bytes);
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);

	           System.out.println("Done Man");

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return "aaa";
	    }
	 
	 @PostMapping("/upload/multi") // //new annotation since 4.3
	 public String uploadFiles(@RequestParam("files") MultipartFile[] files) {
		    String message = "Anandum";
		    try {
		      List<String> fileNames = new ArrayList<>();

		      Arrays.asList(files).stream().forEach(file -> {
		        save(file);
		        fileNames.add(file.getOriginalFilename());
		      });

		      message = "Uploaded the files successfully: " + fileNames;
		      return "sss";
		    } catch (Exception e) {
		      message = "Fail to upload files!";
		      return "eee";
		    }
		  }
	 
	 
	 public void save(MultipartFile file) {
		    try {
		      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		    } catch (Exception e) {
		      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		    }
		  }

}
