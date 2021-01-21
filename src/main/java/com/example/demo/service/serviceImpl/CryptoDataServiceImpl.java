package com.example.demo.service.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.example.demo.service.CryptoDataService;

/**
 * This class is used for mainly containing the Encryption and Decryption
 * Strategies. This class contains three methods 1.Encrypt : This method mainly
 * used for Encrypting Data,And here we used AES algorithm. 2.Set key User: This
 * method used for Set the Key and here we used MD5 and SHA-1. 3.Decrypt : This
 * method is used for decrypting the Data from encrypted form.
 * 
 * @author M1056279
 * @version 1.0
 * @since 12/06/2020
 *
 */
@Service
public class CryptoDataServiceImpl implements CryptoDataService {

	private static SecretKeySpec secretKey;
	private static byte[] key;

	/**
	 * Returns an String of Encrypted form.
	 * 
	 * @param strToEncrypt as first input arguments of original String to encrypt.
	 * @param secret as an Second input parameter.
	 * @return the String in Encrypted format after it get saved.
	 * 
	 */
	@Override
	public String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/**
	 * Returns an Secret key.
	 * 
	 * @param myKey takes one arguments as key.
	 * 
	 */
	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns an String of Decrypted form.
	 * 
	 * @param strToDecrypt as first input in Encrypted String form.
	 * @param secret as an Second input parameter.
	 * @return the String in Decrypted format after it get saved.
	 * 
	 */
	@Override
	public String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

}
