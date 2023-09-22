package com.lts.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lts.model.User;
import com.lts.service.UserService;

@RestController
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;
	
	HashMap<String,Object> response = new HashMap<String,Object>();
	
	
	@PostMapping("/register")
	public Map<String,Object> register(@RequestBody User user) {
		
		if(!userService.checkUserExistence(user.getEmail())) {
			userService.registerUser(user);
			response.put("message","Created");
			response.put("statusCode",HttpStatus.CREATED);
			response.put("data",user);
			return response;
		}
		response.put("message","User Already exists");
		response.put("statusCode",HttpStatus.BAD_REQUEST);
		response.put("data", user);
		return response;
	}
	

	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/login")
	public HashMap<String,Object> login(@RequestBody User userLogin) {
		HashMap<String,Object> response = new HashMap<String,Object>();
		Optional<User> user= userService.getUserById(userLogin.getEmail());
		if(!user.isPresent()) {
			response.put("message","User Doesn't exists");
			response.put("statusCode",HttpStatus.NOT_FOUND);
			return response;
		}

		User user1 = user.get();
		if(!user1.getPassword().equals(userLogin.getPassword())) {
			response.put("message","Invalid Password");
			response.put("statusCode",HttpStatus.NO_CONTENT);
			return response;
		}
		String message = String.format("Welcome %s", user1.getName());
		response.put("message",message);
		response.put("statusCode",HttpStatus.OK);
		response.put("data", user);
		return response;		
				
	}
}
