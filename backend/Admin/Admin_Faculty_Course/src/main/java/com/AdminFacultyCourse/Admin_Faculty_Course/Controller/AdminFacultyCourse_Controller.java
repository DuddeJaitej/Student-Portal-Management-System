package com.AdminFacultyCourse.Admin_Faculty_Course.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.AdminFacultyCourse.Admin_Faculty_Course.Entity.AdminFacultyCourse;
import com.AdminFacultyCourse.Admin_Faculty_Course.Service.AdminFacultyCourse_Service;

@RestController
@RequestMapping("/admin/admin-faculty-course")
@CrossOrigin(origins = "*")
public class AdminFacultyCourse_Controller {
	
	@Autowired
	private AdminFacultyCourse_Service service;
	
	@PostMapping("/create-admin-faculty-course")
	public AdminFacultyCourse createCourse(@RequestBody AdminFacultyCourse course) {
		return service.createCourse(course);
	}
	
	@GetMapping("/getMail")
	public List<String> getAllFacultyEmails(){
		return service.getAllFacultyEmails();
	}
	
	@PostMapping("/emails-by-names")
    public List<String> getFacultyEmailsByNames(@RequestBody List<String> names) {
        return service.getFacultyEmailsByNames(names);
    }
	
	@GetMapping("/by-faculty")
	public List<AdminFacultyCourse> getCourseByFaculty(@RequestParam String facultyName, @RequestParam String registerNumber){
		return service.getCoursesByFaculty(facultyName, registerNumber);
	}

	@GetMapping("/by-registration/{registrationNumber}")
	public List<AdminFacultyCourse> getCoursesByRegistration(@PathVariable String registrationNumber) {
		return service.getCoursesByRegistrationNumber(registrationNumber);
	}
}
