package com.lts1.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lts1.model.User;
import com.lts1.serviceImpl.UserServiceImpl;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	private UserServiceImpl userService;
	
	HashMap<String,Object> response = new HashMap<String,Object>();

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody User user) {
		if(!userService.checkUserExistence(user.getEmail())) {
			user.setNumberOfLeaves(5);
			user.setRemainingLeaves(5);
			userService.registerUser(user);
			response.put("message","Created");
			response.put("statusCode",HttpStatus.CREATED);
			response.put("data",user);
			return ResponseEntity.ok(response);
		}
		response.put("message","User Already exists");
		response.put("statusCode",HttpStatus.BAD_REQUEST);
		response.put("data", user);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody User userLogin) {
		Optional<User> user= userService.getUserById(userLogin.getEmail());
		if(!user.isPresent()) {
			response.put("message","User Doesn't exists");
			response.put("statusCode",HttpStatus.NOT_FOUND);
			return ResponseEntity.ok(response);
		}

		User user1 = user.get();
		if(!user1.getPassword().equals(userLogin.getPassword())) {
			response.put("message","Invalid Password");
			response.put("statusCode",HttpStatus.NO_CONTENT);
			return ResponseEntity.ok(response);
		}
		String message = String.format("Welcome %s", user1.getName());
		response.put("message",message);
		response.put("statusCode",HttpStatus.OK);
		response.put("data", user);
		return ResponseEntity.ok(response);		
				
	}
}
