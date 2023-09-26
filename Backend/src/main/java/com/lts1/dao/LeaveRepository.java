package com.lts1.dao;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lts1.model.Leaves;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves,Integer>{
	
	List<Leaves> findAllByEmail(String email);
	
	@Query("select * from leaves where status=?")
	public List<Leaves> findAllByStatus(String status);


}
