package com.AdminStudentCourse.Admin_Student_Course.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Admin_Student_Course")
public class AdminStudentCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "CourseCode")
	private String courseCode;
	@Column(name = "CourseName")
	private String courseName;
	@Column(name = "FacultyName")
	private String facultyName;
	@Column(name = "Section")
	private String section;
	@Column(name = "Role")
	private String role = "Admin";
	
	public AdminStudentCourse() {};
	
	public AdminStudentCourse(String courseCode, String courseName, String facultyName, String section) {
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.facultyName = facultyName;
		this.section = section;
	}
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
	
}
