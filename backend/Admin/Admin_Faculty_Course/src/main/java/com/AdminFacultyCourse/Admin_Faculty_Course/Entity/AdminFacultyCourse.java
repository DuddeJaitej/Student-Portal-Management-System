package com.AdminFacultyCourse.Admin_Faculty_Course.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Entity
@Data
@Table(name="Admin_Faculty_Course")
public class AdminFacultyCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	
	@Column(name="Registration_Number")
	@JsonAlias("courseCode")
	private String registrationNumber;
	
	@Column(name="Course_Name")
	private String CourseName;
	
	@Column(name="Faculty_Name")
	private String FacultyName;
	
	@Column(name="Section")
	private String Section;
	
	@Column(name = "Role")
	private String role = "Admin";
	
	public AdminFacultyCourse() {}

	public AdminFacultyCourse(String registrationNumber, String courseName, String facultyName, String section) {
		this.registrationNumber = registrationNumber;
		this.CourseName = courseName;
		this.FacultyName = facultyName;
		this.Section = section;
	}

	public String getRegisterNumber() {
		return registrationNumber;
	}

	public void setRegisterNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getCourseName() {
		return CourseName;
	}

	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public String getFacultyName() {
		return FacultyName;
	}

	public void setFacultyName(String facultyName) {
		FacultyName = facultyName;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}
	
	
}
