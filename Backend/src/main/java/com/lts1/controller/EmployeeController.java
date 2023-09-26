package com.lts1.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lts1.model.Leaves;
import com.lts1.service.LeaveService;
import com.lts1.serviceImpl.LeaveServiceImpl;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	HashMap<String, Object> response = new HashMap<>();
	
	@PostMapping("/apply-leave")
	public HashMap<String,Object> applyLeave(@RequestBody Leaves leave) {
		leaveService.applyLeave(leave);
		response.put("message","Leave Applied Successfully");;
		response.put("data", leave);
		response.put("statusCode", HttpStatus.CREATED);
		return response;	
	}
	
	@GetMapping("/leaves/{email}")
	public HashMap<String, Object> getUserLeaves(@PathVariable String email ){
		response.put("message", "Recieved");
		response.put("statusCode",HttpStatus.OK);
		response.put("data",leaveService.getUserLeaves(email));
		return response;
	}
	
	@DeleteMapping("/leave/{id}")
	public HashMap<String,Object> deleteLeave(@PathVariable int id) {
		System.out.println("hi");
		leaveService.deleteLeave(id);
		response.put("message", "Deleted Successfully");
		response.put("statusCode", HttpStatus.OK);
		response.put("data",null);
		return response;
		
	}
	@PatchMapping("/edit-leave/{id}")
	public HashMap<String,Object> editLeave(@PathVariable int id , @RequestBody Leaves leave) {
		Leaves leave1 = leaveService.checkExistence(id);
		leaveService.saveLeave(leave1);
		response.put("message","Leave Edited Successfully");;
		response.put("data", leave1);
		response.put("statusCode", HttpStatus.NO_CONTENT);
		return response;
	}
	
	@GetMapping("/get-leave/{id}")
	public HashMap<String,Object> getLeave(@PathVariable int id){
		Leaves leave = leaveService.checkExistence(id);
		response.put("data", leave);
		response.put("message", "Recieved");
		response.put("statusCode", HttpStatus.OK);
		return response;
	}
}
