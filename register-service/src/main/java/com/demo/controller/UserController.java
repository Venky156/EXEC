package com.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.User;
import com.demo.response.UserResponse;
import com.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@PostMapping("/register")
	public UserResponse register(@RequestBody User user) {
		return userService.register(user);
	}

	@PostMapping("/login")
	public UserResponse login(@RequestBody User user) {
		return userService.login(user);
	}
}
