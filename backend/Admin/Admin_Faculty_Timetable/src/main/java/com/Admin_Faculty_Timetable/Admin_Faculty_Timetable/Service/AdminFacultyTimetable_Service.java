package com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Entity.AdminFacultyTimetable_Entity;
import com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Repository.AdminFacultyTimetable_Repository;

@Service
public class AdminFacultyTimetable_Service {

    @Autowired
    AdminFacultyTimetable_Repository repo;

    public List<AdminFacultyTimetable_Entity> createTimetable(List<AdminFacultyTimetable_Entity> faculty){
        if (faculty == null || faculty.isEmpty()) {
            throw new IllegalArgumentException("Timetable list cannot be empty");
        }

        for(AdminFacultyTimetable_Entity entity : faculty) {
            if(entity.getProgramName() == null || entity.getProgramName().trim().isEmpty()) {
                throw new IllegalArgumentException("Program name is required");
            }
            if (entity.getDay() == null || entity.getDay().trim().isEmpty()) {
                throw new IllegalArgumentException("Day is required for all timetable entries");
            }
            
            if (entity.getFacultyName() != null) {
                entity.setFacultyName(entity.getFacultyName().trim());
            }
        }

        return repo.saveAll(faculty);
    }

    public List<AdminFacultyTimetable_Entity> getTimetableByProgram(String programName){
        return repo.findByProgramName(programName);
    }
    
    public List<AdminFacultyTimetable_Entity> getTimetableByFaculty(String facultyName){
    	if(facultyName == null || facultyName.trim().isEmpty()) {
    		return List.of();
    	}
    	return repo.findByFacultyName(facultyName.trim());
    }
    
}