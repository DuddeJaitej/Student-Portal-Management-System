package com.Fee.MyFee.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fee.MyFee.Entity.MyFee_Entity;

@Repository
public interface MyFee_Repository extends JpaRepository<MyFee_Entity, Long> {

	void deleteByProgramName(String program);
	
	List<MyFee_Entity> findByProgramName(String programName);
}
