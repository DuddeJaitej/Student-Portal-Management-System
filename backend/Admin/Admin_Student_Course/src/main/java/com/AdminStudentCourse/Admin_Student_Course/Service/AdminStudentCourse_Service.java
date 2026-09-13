package com.AdminStudentCourse.Admin_Student_Course.Service;

import java.util.List;

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
	
	public List<AdminStudentCourse> getAllCourses(){
		return repository.findAll();
	}
	
	public List<AdminStudentCourse> getCoursesByFaculty(String facultyName){
		return repository.findByFacultyName(facultyName);
	}
	
	public List<AdminStudentCourse> getCoursesBySection(String section){
		return repository.findBySection(section);
	}
	
	public AdminStudentCourse getCourseByCode(String courseCode){
		return repository.findByCourseCode(courseCode);
	}
	
}
