package com.AdminStudentCourse.Admin_Student_Course.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AdminStudentCourse.Admin_Student_Course.Entity.AdminStudentCourse;

import Admin_Student.Repository.AdminStudentCourse_Repository;

@Service
public class AdminStudentCourse_Service {
	
	@Autowired
	private AdminStudentCourse_Repository repository;
	
	//Create
	public AdminStudentCourse createCourse(AdminStudentCourse course) {
		return repository.save(course);
	}
	
}
