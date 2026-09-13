package com.Fee.MyFee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Fee.MyFee.Entity.MyFee_Entity;
import com.Fee.MyFee.Repository.MyFee_Repository;

@Service
public class MyFee_Service {
	
	@Autowired
	private MyFee_Repository repo;
	
	public List<MyFee_Entity> createFee(List<MyFee_Entity> createFee) {
		
		if(!createFee.isEmpty()) {
			String program = createFee.get(0).getProgramName();
			repo.deleteByProgramName(program);
		}
		return repo.saveAll(createFee);
	}
	
	public List<MyFee_Entity> getFeesByProgram(String programName) {
		return repo.findByProgramName(programName);
	}
	
	public List<MyFee_Entity> getAllFees() {
		return repo.findAll();
	}
	
	public void deleteByProgramName(String programName) {
		repo.deleteByProgramName(programName);
	}
	
}
