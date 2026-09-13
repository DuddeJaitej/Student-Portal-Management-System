package com.Faculty_Home.FacultyHome.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Admin_Faculty_Course")
public class FacultyHome_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Registration_Number")
    private String registrationNumber;

    @Column(name = "Course_Name")
    private String courseName;

    @Column(name = "Faculty_Name")
    private String facultyName;

    @Column(name = "Section")
    private String section;

    // Constructors
    public FacultyHome_Entity() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { 
        this.registrationNumber = registrationNumber; 
    }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { 
        this.courseName = courseName; 
    }

    public String getFacultyName() { return facultyName; }
    public void setFacultyName(String facultyName) { 
        this.facultyName = facultyName; 
    }

    public String getSection() { return section; }
    public void setSection(String section) { 
        this.section = section; 
    }
}