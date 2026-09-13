package com.admin_Students.Admin_Students.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.admin_Students.Admin_Students.Entity.AdminStudents_Entity;
import com.admin_Students.Admin_Students.Repository.AdminStudents_Repository;

@Service
public class AdminStudents_Service {

	@Autowired
	private AdminStudents_Repository repo;
	
	//Create or Save
	public AdminStudents_Entity createStudent(AdminStudents_Entity student) {
		return repo.save(student);
	}

	public long getTotalCount() {
		return repo.count();
	}

	public Map<String, Long> getCountBySection() {
		List<Object[]> results = repo.countStudentsBySection();
	    Map<String, Long> map = new HashMap<>();
	    for (Object[] row : results) {
	        map.put((String) row[0], (Long) row[1]);
	    }
	    return map;
	}
	
	
	public List<AdminStudents_Entity> getBySection(String section){
		return repo.findBySection(section);
	}
	
	public List<AdminStudents_Entity> getAllStudents(){
		return repo.findAll();
	}
	
	public Optional<AdminStudents_Entity> getStudentById(Long id){
		return repo.findById(id);
	}
	
	public Optional<AdminStudents_Entity> getStudentByRegNo(String registerNo){
		return repo.findByRegisterNo(registerNo);
	}
	
	public List<AdminStudents_Entity> searchStudents(String query){
		if (query == null || query.trim().isEmpty()) {
			return List.of();
		}
		return repo.searchStudents(query.trim());
	}
	
}
