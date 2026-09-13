package com.admin_Students.Admin_Students.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Admin_Students")
public class AdminStudents_Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "Name")
	private String name;
	@Column(name = "Register_Number")
	private String registerNo;
	@Column(name = "Email")
	private String email;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "Section")
	private String section;
	@Column(name = "Role")
	private String role = "Admin";
	
	public AdminStudents_Entity() {}
	
	public AdminStudents_Entity(String name, String registerNo, String email, String section) {
		this.name = name;
		this.registerNo = registerNo;
		this.email = email;
		this.section = section;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}	
	
}
