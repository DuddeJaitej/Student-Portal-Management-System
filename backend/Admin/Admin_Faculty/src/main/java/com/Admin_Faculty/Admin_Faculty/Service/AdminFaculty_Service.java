package com.Admin_Faculty.Admin_Faculty.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Admin_Faculty.Admin_Faculty.Entity.AdminFaculty_Entity;
import com.Admin_Faculty.Admin_Faculty.Repository.AdminFaculty_Repository;

@Service
public class AdminFaculty_Service {

	@Autowired
	AdminFaculty_Repository repo;
	
	//Create or Save
	public AdminFaculty_Entity createStudent(AdminFaculty_Entity faculty) {
		return repo.save(faculty);
	}

	public long getTotalCount() {
		return repo.count();
	}

	public Map<String, Long> getCountBySection() {
		List<Object[]> results = repo.countBySection();
	    Map<String, Long> map = new HashMap<>();
	    for (Object[] row : results) {
	        map.put((String) row[0], (Long) row[1]);
	    }
	    return map;
	}
	
	public List<AdminFaculty_Entity> searchFaculty(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        return repo.searchFaculty(query.trim());
    }
	
	public List<AdminFaculty_Entity> getAllFaculty(){
		return repo.findAll();
	}
	
	public Optional<AdminFaculty_Entity> getFacultyById(Long id){
		return repo.findById(id);
	}

	public Optional<AdminFaculty_Entity> getFacultyByRegisterNo(String registerNo) {
		return repo.findByRegisterNoIgnoreCase(registerNo);
	}
	
}
