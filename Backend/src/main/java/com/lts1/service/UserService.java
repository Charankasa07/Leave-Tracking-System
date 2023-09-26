package com.lts1.service;

import java.util.List;
import java.util.Optional;

import com.lts1.model.User;

public interface UserService {
	public boolean checkUserExistence(String email);
	
	public void registerUser(User user);
	
	public List<User> getAllUsers();
	
	public Optional<User> getUserById(String email);
	
	public void saveUser(User user);
	
	public void onAcceptLeave(String email);
}
