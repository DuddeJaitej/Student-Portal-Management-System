package com.AdminStudentCourse.Admin_Student_Course.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AdminStudentCourse.Admin_Student_Course.Entity.AdminStudentCourse;
import com.AdminStudentCourse.Admin_Student_Course.Service.AdminStudentCourse_Service;

@RestController
@RequestMapping("/admin/admin-student-course")
@CrossOrigin("*")
public class AdminStudentCourse_Controller {

	@Autowired
	private AdminStudentCourse_Service service;
	
	@PostMapping("/create_admin-student-course")
	public AdminStudentCourse createCourse(@RequestBody AdminStudentCourse course) {
		return service.createCourse(course);
	}
}
