package com.lts1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lts1.dao.UserRepository;
import com.lts1.model.User;
import com.lts1.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	public boolean checkUserExistence(String email) {
		if(userRepo.existsById(email)) {
			return true;
		}
		return false;
	}
	
	public void registerUser(User user) {
		user.setNumberOfLeaves(0);
		userRepo.save(user);
	}
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	public Optional<User> getUserById(String email) {
		return userRepo.findById(email);
	}
	
	public void saveUser(User user) {
		userRepo.save(user);
	}
	
	public void onAcceptLeave(String email) {
		Optional<User> user = getUserById(email);
		if(user.isPresent()) {
			User user1 = user.get();
			user1.setRemainingLeaves(user1.getRemainingLeaves() - 1);
			userRepo.save(user1);
		}
	}
}
