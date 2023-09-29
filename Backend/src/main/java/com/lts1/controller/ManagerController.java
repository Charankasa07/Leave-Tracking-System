package com.lts1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lts1.model.Leaves;
import com.lts1.model.User;
import com.lts1.serviceImpl.LeaveServiceImpl;
import com.lts1.serviceImpl.UserServiceImpl;
import com.lts1.serviceImpl.MailSenderService;

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {
	
	@Autowired
	private LeaveServiceImpl leaveService;
	
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private MailSenderService mailSenderService;
	
	HashMap<String,Object> response = new HashMap<String,Object>();

	
	@GetMapping("/new-requests")
	public ResponseEntity<Object> getPendingLeaves(){
		response.put("data",leaveService.getPendingLeaves());
		response.put("message", "Data Received");
		response.put("statusCode", HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/overview")
	public ResponseEntity<Object>getOverviewLeaves(){
		response.put("message", "Data Received");
		response.put("data",leaveService.getAllLeaves());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/react-to-leave/{id}/{action}")
	public ResponseEntity<Object> reactToLeave(@RequestBody String message,@PathVariable int id,@PathVariable String action)throws Exception{
		Leaves leave = leaveService.checkExistence(id);
		leave.setStatus(action);
		leave.setMessage(message);
		String pattern = "EEEEEE dd MMM yyyy HH:mm a";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(leave.getStartDate());
		Date endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(leave.getEndDate());
		if(action.equals("accepted")) {
			userService.onAcceptLeave(leave.getEmail());
		}
		mailSenderService.sendEmail(leave.getEmail(), "Reg : Status of the Leave","Dear " + leave.getName() + ",\n Your leave request of type "+ leave.getType()+" from " + simpleDateFormat.format(startDate) + " to " + simpleDateFormat.format(endDate) + " is " + action.toUpperCase() );
		leaveService.saveLeave(leave);
		response.put("data", leave);
		response.put("statusCode", HttpStatus.OK);
		response.put("message","Reacted Successfully");
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/update-leave-count/{count}")
	public ResponseEntity<Object> updateLeaveCount(@PathVariable int count) {
		List<User> users = userService.getAllUsers();
		boolean flag = true;
		for (User u : users) {
			if (u.getRole().equals("employee")) {
				List<Leaves> leaves = leaveService.getUserLeaves(u.getEmail());
				int acceptedCount = 0;
				for (Leaves l : leaves) {
					if (l.getEmail().equals(u.getEmail()) && l.getStatus().equals("accepted")) {
						acceptedCount += 1;
					}
				}
				if (acceptedCount > count) {
					flag = false;
				}
			}
		}
		if (flag) {
			for (User u : users) {
				u.setNumberOfLeaves(count);
				if (u.getRole().equals("employee")) {
					List<Leaves> leaves = leaveService.getUserLeaves(u.getEmail());
					int acceptedCount = 0;
					for (Leaves l : leaves) {
						if (l.getEmail().equals(u.getEmail()) && l.getStatus().equals("accepted")) {
							acceptedCount += 1;
						}
					}
					u.setRemainingLeaves(u.getNumberOfLeaves() - acceptedCount);
					userService.saveUser(u);
				}
			}
			response.put("message", "Leave count updated successfully");
			response.put("statusCode", HttpStatus.OK);
			return ResponseEntity.ok(response);
		}
		response.put("message", "One or More Employee's having accepted leaves greater than given leave count");
		response.put("statusCode",HttpStatus.CONFLICT);
		return ResponseEntity.ok(response);
	}
}
