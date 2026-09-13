package com.Admin_Dashboard.AdminDashboard.Entity;

import java.util.Map;

public class AdminDashboard_Entity {

    private Long totalStudents;
    private Long totalFaculty;
    private Map<String, Long> studentsByProgram;
    private Map<String, Long> facultyByProgram;

    public AdminDashboard_Entity() {}

    public AdminDashboard_Entity(Long totalStudents, Long totalFaculty,
                                 Map<String, Long> studentsByProgram,
                                 Map<String, Long> facultyByProgram) {
        this.totalStudents = totalStudents;
        this.totalFaculty = totalFaculty;
        this.studentsByProgram = studentsByProgram;
        this.facultyByProgram = facultyByProgram;
    }

    public long getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Long totalStudents) { this.totalStudents = totalStudents; }

    public long getTotalFaculty() { return totalFaculty; }
    public void setTotalFaculty(Long totalFaculty) { this.totalFaculty = totalFaculty; }

    public Map<String, Long> getStudentsByProgram() { return studentsByProgram; }
    public void setStudentsByProgram(Map<String, Long> studentsByProgram) { this.studentsByProgram = studentsByProgram; }

    public Map<String, Long> getFacultyByProgram() { return facultyByProgram; }
    public void setFacultyByProgram(Map<String, Long> facultyByProgram) { this.facultyByProgram = facultyByProgram; }
}