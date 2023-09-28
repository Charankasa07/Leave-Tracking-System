package com.lts1.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Leaves{
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String email;
	private String type;
	private
String startDate;
	private
String endDate;
	private String reason;
	private String status;
	private String message;

	public Leaves(){}
	
	public Leaves(String name, String email, String type,
String startDate,
String endDate, String reason,
			String status, String message) {
		this.name = name;
		this.email = email;
		this.type = type;
		this.startDate = startDate;
		this.endDate = endDate;
		this.reason = reason;
		this.status = status;
		this.message = message;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String managerReason) {
		this.message = managerReason;
	}
	public
String getStartDate() {
		return startDate;
	}
	public void setStartDate(
String startDate) {
		this.startDate = startDate;
	}
	public
String getEndDate() {
		return endDate;
	}
	public void setEndDate(
String endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "Leaves [id=" + id + ", email=" + email + ", type=" + type + ", startDate=" + startDate + ", endDate="
				+ endDate + ", reason=" + reason + ", status=" + status + ", message=" + message + "]";
	}	
	
	
}