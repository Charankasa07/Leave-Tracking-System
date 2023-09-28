package com.lts1.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lts1.dao.LeaveRepository;
import com.lts1.model.Leaves;
import com.lts1.service.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService{
	@Autowired
	private LeaveRepository leaveRepo;
	
	public void applyLeave(Leaves leave) {
		leaveRepo.save(leave);
	}
	
	public List<Leaves> getUserLeaves(String email){
		return leaveRepo.findAllByEmail(email);
	}
	
	public void deleteLeave(int id) {
		leaveRepo.deleteById(id);
	}


	public List<Leaves> getPendingLeaves() {
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
