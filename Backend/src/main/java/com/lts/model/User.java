package com.lts.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
	private String name;
	private String phone;
	@Id
	private String email;
	private String password;
	private String role;
	private int numberOfLeaves;
	private int remainingLeaves;
	
	public User() {
		
	}
	
	
	public User(String name, String phone, String email, String password, String role, int numberOfLeaves) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.role = role;
		this.numberOfLeaves = numberOfLeaves;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getNumberOfLeaves() {
		return numberOfLeaves;
	}
	public void setNumberOfLeaves(int numberOfLeaves) {
		this.numberOfLeaves = numberOfLeaves;
	}
	public int getRemainingLeaves() {
		return remainingLeaves;
	}


	public void setRemainingLeaves(int remainingLeaves) {
		this.remainingLeaves = remainingLeaves;
	}


	@Override
	public String toString() {
		return "User [name=" + name + ", phone=" + phone + ", email=" + email + ", password=" + password + ", role="
				+ role + ", numberOfLeaves=" + numberOfLeaves + "]";
	}
	
}

