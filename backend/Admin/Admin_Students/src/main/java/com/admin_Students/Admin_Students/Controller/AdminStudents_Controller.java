package com.admin_Students.Admin_Students.Controller;

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

import com.admin_Students.Admin_Students.Entity.AdminStudents_Entity;
import com.admin_Students.Admin_Students.Service.AdminStudents_Service;

@RestController
@RequestMapping("/Admin/Admin-Students")
@CrossOrigin("*")
public class AdminStudents_Controller {

	@Autowired
	private AdminStudents_Service service;
	
	@PostMapping("/Create_Admin-Students")
	public ResponseEntity<AdminStudents_Entity> createStudent(@RequestBody AdminStudents_Entity student) {
		try {
			AdminStudents_Entity saved = service.createStudent(student);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	@GetMapping("/count")
	public long getTotalStudents() {
	    return service.getTotalCount();
	}

	@GetMapping("/by-section")
	public Map<String, Long> getStudentsBySection() {
	    return service.getCountBySection();
	}
	
	@GetMapping("/section/{section}")
	public List<AdminStudents_Entity> getBySection(@PathVariable String section){
		return service.getBySection(section);
	}
	
	@GetMapping("/all")
	public List<AdminStudents_Entity> getAllStudents(){
		return service.getAllStudents();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AdminStudents_Entity> getStudentById(@PathVariable Long id){
		Optional<AdminStudents_Entity> student = service.getStudentById(id);
		return student.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/by-reg/{registerNo}")
	public ResponseEntity<AdminStudents_Entity> getStudentByRegNo(@PathVariable String registerNo){
		Optional<AdminStudents_Entity> student = service.getStudentByRegNo(registerNo);
		return student.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/by-reg/{registerNo}")
	public ResponseEntity<AdminStudents_Entity> updateStudentByRegNo(
			@PathVariable String registerNo, @RequestBody AdminStudents_Entity update) {
		Optional<AdminStudents_Entity> existing = service.getStudentByRegNo(registerNo);
		if (existing.isEmpty()) return ResponseEntity.notFound().build();
		AdminStudents_Entity student = existing.get();
		student.setName(update.getName());
		student.setEmail(update.getEmail());
		student.setPhone(update.getPhone());
		student.setSection(update.getSection());
		student.setRegisterNo(update.getRegisterNo());
		return ResponseEntity.ok(service.createStudent(student));
	}
	
	@GetMapping("/search")
	public List<AdminStudents_Entity> searchStudents(@RequestParam String query){
		return service.searchStudents(query);
	}
}
