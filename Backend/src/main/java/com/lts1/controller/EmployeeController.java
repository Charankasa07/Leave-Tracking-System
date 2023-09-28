package com.lts1.controller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.lts1.serviceImpl.LeaveServiceImpl;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	HashMap<String, Object> response = new HashMap<>();
	
	@PostMapping("/apply-leave")
	public ResponseEntity<Object>  applyLeave(@RequestBody Leaves leave) {
		leaveService.applyLeave(leave);
		response.put("message","Leave Applied Successfully");;
		response.put("data", leave);
		response.put("statusCode", HttpStatus.CREATED);
		return ResponseEntity.ok(response);	
	}
	
	@GetMapping("/get-leaves/{email}")
	public ResponseEntity<Object> getUserLeaves(@PathVariable String email ){
		response.put("message", "Recieved");
		response.put("statusCode",HttpStatus.OK);
		response.put("data",leaveService.getUserLeaves(email));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/delete-leave/{id}")
	public ResponseEntity<Object>  deleteLeave(@PathVariable int id) {
		leaveService.deleteLeave(id);
		response.put("message", "Deleted Successfully");
		response.put("statusCode", HttpStatus.NO_CONTENT);
		response.put("data",null);
		return ResponseEntity.ok(response);
		
	}
	@PostMapping("/edit-leave/{id}")
	public ResponseEntity<Object>  editLeave(@PathVariable int id , @RequestBody Leaves leave) {
		Leaves leave1 = leaveService.checkExistence(id);
		if(leave1!=null){
			System.out.println(leave);
			leaveService.saveLeave(leave);
			response.put("message","Leave Edited Successfully");;
			response.put("data", leave);
			response.put("statusCode", HttpStatus.NO_CONTENT);
			return ResponseEntity.ok(response);
		}else{
			response.put("message","Leave not found");
			response.put("data","");
			response.put("statusCode",HttpStatus.NOT_FOUND);
			return ResponseEntity.ok(response);
		}
	}
	
	@GetMapping("/get-leave/{id}")
	public ResponseEntity<Object>  getLeave(@PathVariable int id){
		Leaves leave = leaveService.checkExistence(id);
		response.put("data", leave);
		response.put("message", "Recieved");
		response.put("statusCode", HttpStatus.OK);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get-leave-history/{email}")
	public ResponseEntity<Object> getLeaveHistory(@PathVariable String email){
		java.util.List<Leaves> leaves = leaveService.getUserLeaves(email);
		int acceptedCount = 0;
		int rejectedCount=0;
		int pendingCount=0;
		for(Leaves leave : leaves){
			if(leave.getStatus().equals("accepted")){
				acceptedCount+=1;
			}
			else if(leave.getStatus().equals("rejected")){
				rejectedCount+=1;
			}
			else{
				pendingCount+=1;
			}
		}
		response.put("message","Done");
		response.put("acceptedCount",acceptedCount);
		response.put("rejectedCount",rejectedCount);
		response.put("pendingCount",pendingCount);
		response.put("statusCode",HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
}
