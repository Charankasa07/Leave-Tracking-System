package com.lts1.service;

import java.util.List;

import com.lts1.model.Leaves;

public interface LeaveService {
	
	public void applyLeave(Leaves Leave);
	
	public List<Leaves> getUserLeaves(String email);
	
	public void deleteLeave(int id);
	
	public String checkLeaveStatus(int id);
	
	public Leaves checkExistence(int id);
	
	public void saveLeave(Leaves leave);
}
