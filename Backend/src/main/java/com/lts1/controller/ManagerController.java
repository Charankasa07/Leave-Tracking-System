package com.lts1.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lts1.model.Leaves;
import com.lts1.model.User;
import com.lts1.service.LeaveService;
import com.lts1.service.UserService;
import com.lts1.serviceImpl.LeaveServiceImpl;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	@Autowired
	private UserService userService;
	
	HashMap<String,Object> response = new HashMap<String,Object>();
	
	@GetMapping("/new-requests")
	public HashMap<String, Object> getPendingLeaves(){
		response.put("data",leaveService.getPendingLeaves());
		response.put("message", "Data Recieved");
		response.put("statusCode", HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/overview")
	public HashMap<String,Object> getOverviewLeaves(){
		response.put("message", "Data Recieved");
		response.put("data",leaveService.getAllLeaves());
		return response;
	}
	
	@PatchMapping("/leave/{id}/{action}")
	public HashMap<String, Object> reactToLeave(@RequestBody String message,@PathVariable int id,@PathVariable String action){
		Leaves leave = leaveService.checkExistence(id);
		System.out.println(message);
		leave.setStatus(action);
		leave.setMessage(message);
		if(action.equals("accepted")) {
			userService.onAcceptLeave(leave.getEmail());
		}
		leaveService.saveLeave(leave);
		response.put("data", leave);
		response.put("statusCode", HttpStatus.OK);
		response.put("message","Reacted Successfully");
		return response;
		
	}
	
	@PostMapping("/{count}")
	public HashMap<String,Object> updateLeaveCount(@PathVariable int count) {
		List<User> users = userService.getAllUsers();
		for(User u : users) {
			u.setNumberOfLeaves(count);
			if(u.getRole().equals("employee")) {
				List<Leaves> leaves = leaveService.getUserLeaves(u.getEmail());
				int count1=0;
				for(Leaves l:leaves) {
					if(l.getEmail().equals(u.getEmail()) && l.getStatus().equals("accepted")) {
						count1+=1;
					}
				}
				u.setRemainingLeaves(u.getNumberOfLeaves()-count1);
				userService.saveUser(u);
			}
		}
		response.put("message","Leave count updated successfully");
		response.put("statusCode", HttpStatus.OK);
		return response;
		
	}
}
