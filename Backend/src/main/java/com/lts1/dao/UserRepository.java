package com.lts1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lts1.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
}
