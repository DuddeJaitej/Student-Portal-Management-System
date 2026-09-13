package com.Admin_Students_Timetable.Admin_Students_Timetable.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Admin_Students_Timetable.Admin_Students_Timetable.Entity.AdminStudentsTimetable_Entity;
import com.Admin_Students_Timetable.Admin_Students_Timetable.Repository.AdminStudentsTimetable_Repository;


@Service
public class AdminStudentsTimetable_Service {

    @Autowired
    AdminStudentsTimetable_Repository repo;

    public List<AdminStudentsTimetable_Entity> createTimetable(List<AdminStudentsTimetable_Entity> faculty){
        if (faculty == null || faculty.isEmpty()) {
            throw new IllegalArgumentException("Timetable list cannot be empty");
        }

        for(AdminStudentsTimetable_Entity entity : faculty) {
            if(entity.getProgramName() == null || entity.getProgramName().trim().isEmpty()) {
                throw new IllegalArgumentException("Program name is required");
            }
            if (entity.getDay() == null || entity.getDay().trim().isEmpty()) {
                throw new IllegalArgumentException("Day is required for all timetable entries");
            }
        }

        faculty.get(0).getProgramName();
        return repo.saveAll(faculty);
    }

    public List<AdminStudentsTimetable_Entity> getTimetableByProgram(String programName){
        return repo.findByProgramName(programName);
    }
    
    public List<AdminStudentsTimetable_Entity> getTimetableByDay(String day){
        if (day == null || day.trim().isEmpty()) {
            return List.of();
        }
        return repo.findByDay(day.trim());
    }
    
    public List<AdminStudentsTimetable_Entity> getTimetableByProgramAndDay(String programName, String day){
        if (programName == null || programName.trim().isEmpty() || day == null || day.trim().isEmpty()) {
            return List.of();
        }
        return repo.findByProgramNameAndDay(programName.trim(), day.trim());
    }
    
}
