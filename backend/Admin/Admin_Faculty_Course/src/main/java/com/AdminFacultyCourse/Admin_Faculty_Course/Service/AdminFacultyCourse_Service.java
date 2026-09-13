package com.AdminFacultyCourse.Admin_Faculty_Course.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AdminFacultyCourse.Admin_Faculty_Course.Entity.AdminFacultyCourse;
import com.AdminFacultyCourse.Admin_Faculty_Course.Repository.AdminFacultyCourse_Repository;

@Service
public class AdminFacultyCourse_Service {

	@Autowired
	private AdminFacultyCourse_Repository repository;
	
	public AdminFacultyCourse createCourse(AdminFacultyCourse course) {
		return repository.save(course);
	}
	

	public List<String> getAllFacultyEmails() {
	    return repository.findAll().stream()
	            .map(this::constructFacultyEmail)   // Convert name to email
	            .filter(email -> email != null && !email.trim().isEmpty())
	            .toList();
	}

	public List<String> getFacultyEmailsByNames(List<String> names) {
	    if (names == null || names.isEmpty()) {
	        return List.of();
	    }

	    return repository.findAll().stream()
	            .filter(f -> names.contains(f.getFacultyName().trim()))
	            .map(this::constructFacultyEmail)
	            .filter(email -> email != null && !email.trim().isEmpty())
	            .toList();
	}

	// Helper method to create email from Faculty Name
	private String constructFacultyEmail(AdminFacultyCourse faculty) {
	    if (faculty.getFacultyName() == null || faculty.getFacultyName().trim().isEmpty()) {
	        return null;
	    }
	    
	    String name = faculty.getFacultyName().trim().toLowerCase()
	                         .replace(" ", "")
	                         .replace(".", "");
	    
	    return name + "@yourcollege.com";
	}
	
	public List<AdminFacultyCourse> getCoursesByFaculty(String facultyName, String registerNumber){
		if(facultyName == null || registerNumber == null) {
			return List.of();
		}
		
		return repository.findAll().stream().filter(course -> facultyName.trim().equalsIgnoreCase(course.getFacultyName().trim()) && 
				registerNumber.trim().equalsIgnoreCase(course.getRegisterNumber().trim())
				)
				.toList();
	}

	public List<AdminFacultyCourse> getCoursesByRegistrationNumber(String registrationNumber) {
		if (registrationNumber == null || registrationNumber.isBlank()) {
			return List.of();
		}
		String normalized = registrationNumber.trim();
		return repository.findAll().stream()
				.filter(course -> course.getRegisterNumber() != null
						&& normalized.equalsIgnoreCase(course.getRegisterNumber().trim()))
				.toList();
	}
}
