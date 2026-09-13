package com.Admin_Faculty.Admin_Faculty.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Faculty.Admin_Faculty.Entity.AdminFaculty_Entity;
import com.Admin_Faculty.Admin_Faculty.Service.AdminFaculty_Service;

@RestController
@RequestMapping("/Admin/Admin_Faculty")
@CrossOrigin("*")
public class AdminFaculty_Controller {
	
	@Autowired
	AdminFaculty_Service service;
	
	@PostMapping("/Admin_Faculty")
	public ResponseEntity<AdminFaculty_Entity> createStudent(@RequestBody AdminFaculty_Entity create) {
		try {
			AdminFaculty_Entity saved = service.createStudent(create);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GetMapping("/count")
	public long getTotalFaculty() {
	    return service.getTotalCount();
	}

	@GetMapping("/by-section")
	public Map<String, Long> getTotalBySection() {
	    return service.getCountBySection();
	}
	

	@GetMapping("/search")
	public ResponseEntity<List<AdminFaculty_Entity>> searchFaculty(@RequestParam String query) {
	    try {
	        List<AdminFaculty_Entity> results = service.searchFaculty(query.trim());
	        return ResponseEntity.ok(results);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.ok(List.of());
	    }
	}
	
	@GetMapping("/all")
	public List<AdminFaculty_Entity> getAllFaculty(){
		return service.getAllFaculty();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AdminFaculty_Entity> getFacultyById(@PathVariable Long id){
		Optional<AdminFaculty_Entity> faculty = service.getFacultyById(id);
		return faculty.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/by-reg/{registerNo}")
	public ResponseEntity<AdminFaculty_Entity> getFacultyByRegisterNo(@PathVariable String registerNo) {
		Optional<AdminFaculty_Entity> faculty = service.getFacultyByRegisterNo(registerNo);
		return faculty.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/by-reg/{registerNo}")
	public ResponseEntity<AdminFaculty_Entity> updateFacultyByRegisterNo(
			@PathVariable String registerNo, @RequestBody AdminFaculty_Entity update) {
		Optional<AdminFaculty_Entity> existing = service.getFacultyByRegisterNo(registerNo);
		if (existing.isEmpty()) return ResponseEntity.notFound().build();
		AdminFaculty_Entity faculty = existing.get();
		faculty.setName(update.getName());
		faculty.setEmail(update.getEmail());
		faculty.setPhone(update.getPhone());
		faculty.setSection(update.getSection());
		faculty.setRegisterNo(update.getRegisterNo());
		return ResponseEntity.ok(service.createStudent(faculty));
	}
	
}
