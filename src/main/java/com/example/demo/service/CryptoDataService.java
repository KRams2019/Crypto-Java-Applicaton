package com.example.demo.service;

public interface CryptoDataService {
	

	public String encrypt(String originalString, String secretKey);

	public String decrypt(String encryptedString, String secretKey);


}
