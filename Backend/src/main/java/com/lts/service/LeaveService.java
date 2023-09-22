package com.lts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lts.dao.LeaveRepository;
import com.lts.model.Leaves;

@Service
public class LeaveService {
	@Autowired
	private LeaveRepository leaveRepo;
	
	public void applyLeave(Leaves leave) {
		leaveRepo.save(leave);
	}
	
	public List<Leaves> getUserLeaves(String email){
		return leaveRepo.findAllByEmail(email);
	}
	
	public String deleteLeave(int id) {
		leaveRepo.deleteById(id);
		return "Deleted Successfully";
	}
	
	public String checkLeaveStatus(int id) {
		Optional<Leaves>leave = leaveRepo.findById(id);
		if(leave.isPresent()) {
			Leaves leave1 = leave.get();
			return leave1.getStatus();
		}
		return "";
	}

	public List<Leaves> getPendingLeaves() {
		System.out.println(leaveRepo.findAllByStatus("pending"));
		return leaveRepo.findAllByStatus("pending");
	}

	public List<Leaves> getAllLeaves() {
		return leaveRepo.findAll();
	}
	
	public Leaves checkExistence(int id) {
		Optional<Leaves> leave = leaveRepo.findById(id);
		if(leave.isPresent()) {
			return leave.get();
		}
		return null;
	}
	
	public void saveLeave(Leaves leave) {
		leaveRepo.save(leave);
	}


}
