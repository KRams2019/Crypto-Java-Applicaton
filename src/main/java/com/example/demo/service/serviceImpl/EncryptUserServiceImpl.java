package com.example.demo.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.exception.UserNameCannotEmptyException;
import com.example.demo.exception.UserPasswordCannotEmptyException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CryptoDataService;
import com.example.demo.service.EncryptUserService;

/**
 * This class is used for mainly Encoding and Decoding the User as well as
 * Adding Users. This class contains four methods 1.Add Users:which mainly used
 * for adding/saving user in database. 2.Encode User: This method used for
 * Encoding Users. 3.Decode User: This method used for Decoding Users. 4.Find
 * User: This method is used to search list of users based on given keywords.
 * 
 * @author M1056279
 * @version 1.0
 * @since 12/06/2020
 *
 */
@Service
public class EncryptUserServiceImpl implements EncryptUserService {

	public final static Logger log = Logger.getLogger(EncryptUserServiceImpl.class.getName());

	@Autowired
	UserRepository userRepository;

	@Autowired
	CryptoDataService cryptoData;

	final String secretKey = "";

	ModelMapper modelMapper = new ModelMapper();

	/**
	 * Returns an User object after adding it to database.
	 * 
	 * @param  userDto argument for adding User.
	 * @return the user DTO is returned after it get saved.
	 * @throws ServiceException
	 */
	@Override
	public UserDto addUsers(UserDto userDto) throws ServiceException {
		try {

			User user = convertDtoToEntity(userDto);
			if (user.getUserName().trim().length() == 0 || user.getUserName() == null) {
				throw new UserNameCannotEmptyException("User Name Cannot Be Empty or Null");
			}

			if (user.getPassword().trim().length() == 0 || user.getPassword() == null) {
				throw new UserPasswordCannotEmptyException("User Password Cannot Be Empty or Null");
			}

			User savedUser = userRepository.save(user);
			log.info("Added Users Successfully with user Id " + savedUser.getUserId());
			return convertEntityToDto(savedUser);
		} catch (UserNameCannotEmptyException | UserPasswordCannotEmptyException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	/**
	 * Returns an User object taking encrypted data as input.
	 * 
	 * @param userDto object for Decoding user.
	 * @return the user DTO is returned.
	 * @throws ServiceException
	 */
	@Override
	public UserDto decryptUser(UserDto userDto) throws ServiceException {
		try {
			User user = convertDtoToEntity(userDto);
			if (user.getUserName().trim().length() == 0 || user.getUserName() == null) {
				throw new UserNameCannotEmptyException("User Name Cannot Be Empty or Null");
			}

			if (user.getPassword().trim().length() == 0 || user.getPassword() == null) {
				throw new UserPasswordCannotEmptyException("User Password Cannot Be Empty or Null");
			}
			String userName = cryptoData.decrypt(user.getUserName(), secretKey);
			String password = cryptoData.decrypt(user.getPassword(), secretKey);
			user.setUserName(userName);
			user.setPassword(password);
			UserDto decryptUser = convertEntityToDto(user);
			log.info("Decoded User Successfully with user Name " + userDto.getUserName() + " to " + user.getUserName());
			return decryptUser;
		} catch (UserNameCannotEmptyException | UserPasswordCannotEmptyException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	/**
	 * @return list of userDto Matching with pattern
	 * @param searchUser String as input to find matching pattern
	 *
	 */
	@Override
	public List<UserDto> findUser(String searchUser) {
		List<User> users = userRepository.findBySearchTermNative(searchUser);
		List<UserDto> usersDto = users.stream().map(entity -> convertEntityToDto(entity)).collect(Collectors.toList());
		if (usersDto.size() > 0)
			log.info("Found Users Successfully with Pattern " + searchUser);
		else
			log.info("No Users Found with Pattern " + searchUser);
		return usersDto;
	}

	/**
	 * Returns an User object in encrypted form.
	 * 
	 * @param userDto for encoding User.
	 * @return the user in encrypted form.
	 * @throws ServiceException
	 */
	@Override
	public UserDto encrypytUser(UserDto userDto) throws ServiceException {
		try {
			User user = convertDtoToEntity(userDto);

			if (user.getUserName().trim().length() == 0 || user.getUserName() == null) {
				System.out.println(user.getUserName().trim());
				throw new UserNameCannotEmptyException("User Name Cannot Be Empty or Null");
			}

			if (user.getPassword().trim().length() == 0 || user.getPassword() == null) {
				throw new UserPasswordCannotEmptyException("User Password Cannot Be Empty or Null");
			}

			else {
				String CryptedUserName = cryptoData.encrypt(user.getUserName(), secretKey);
				String CryptedUserPassword = cryptoData.encrypt(user.getPassword(), secretKey);
				user.setUserName(CryptedUserName);
				user.setPassword(CryptedUserPassword);
				UserDto encryptUser = convertEntityToDto(user);
				log.info("Encoded User Successfully with user Name " + userDto.getUserName() + " to "
						+ user.getUserName());
				return encryptUser;
			}
		} catch (UserNameCannotEmptyException | UserPasswordCannotEmptyException e) {
			throw new ServiceException(e.getMessage(), e);
		}

	}

	/**
	 * Used to converting Entity to DTO
	 * 
	 * @param user for fetching user object
	 * @return userDto.
	 */
	public UserDto convertEntityToDto(User user) {
		return modelMapper.map(user, UserDto.class);

	}

	/**
	 * Used to converting DTO to Entity
	 * 
	 * @param userDto for fetching userDto object
	 * @return user.
	 */
	public User convertDtoToEntity(UserDto userDto) {
		return modelMapper.map(userDto, User.class);
	}

}
