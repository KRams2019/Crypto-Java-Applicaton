package com.example.demo;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/** 
* This Application about Encoding and Decoding of user and searching users based upon pattern.
* @author M1056279 
* @version 1.0
* @since   2020-06-12 
*/



@SpringBootApplication
public class CryptoProjectApplication {
	
	private static Logger logger = LogManager.getRootLogger();
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("..\\CryptoProjectApplication\\src\\main\\resources\\log4j2.properties");
		logger.info("Starting the springboot app...");
		SpringApplication.run(CryptoProjectApplication.class, args);
	}

}
