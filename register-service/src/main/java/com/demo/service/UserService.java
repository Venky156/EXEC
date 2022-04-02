package com.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.demo.controller.UserController;
import com.demo.entity.UserEntity;
import com.demo.model.User;
import com.demo.repo.UserRepo;
import com.demo.response.UserResponse;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserResponse register(User user) {
		UserEntity ue = new UserEntity(user.getUserName(), user.getEmail(), user.getPassword());
		UserResponse userResponse = new UserResponse();
		try {
			UserEntity register = userRepo.save(ue);
			logger.info("User iscreated with userId {}", register.getUserId());
			userResponse.setResponseCode("200");
			userResponse.setResponseType("Success");
			userResponse.setResponseText("Registration Completed Successfully");
		} catch (Exception e) {
			logger.info("Error ==> " + e);
			userResponse.setResponseCode("000");
			userResponse.setResponseType("Error");
			if (e instanceof DataIntegrityViolationException) {
				userResponse.setResponseText("User Already Exist..");
			} else {
				userResponse.setResponseText("Please contact your Admin");
			}
		}
		return userResponse;
	}

	public UserResponse login(User user) {
		UserResponse userResponse = new UserResponse();
		try {
			UserEntity login = userRepo.findByUserNameOrEmail(user.getUserName(), user.getEmail());
			if (login == null) {
				userResponse.setResponseCode("000");
				userResponse.setResponseType("Success");
				userResponse.setResponseText("User name or Email is incorrect");
				return userResponse;
			}
			if (login.getPassword().equals(user.getPassword())) {
				logger.info(login.getUserName() + " Login Successfull...");
				userResponse.setResponseCode("200");
				userResponse.setResponseType("Success");
				userResponse.setResponseText("Login Successfully");
			} else {
				userResponse.setResponseCode("000");
				userResponse.setResponseType("Success");
				userResponse.setResponseText("Password is incorrect");
			}

		} catch (Exception e) {
			logger.info("Error ==> " + e);
			userResponse.setResponseCode("000");
			userResponse.setResponseType("Error");
			userResponse.setResponseText("Login Failed");
		}
		return userResponse;
	}
}
