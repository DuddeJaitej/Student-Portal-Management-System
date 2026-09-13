package com.Admin_Students_Timetable.Admin_Students_Timetable.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admin_Students_Timetable.Admin_Students_Timetable.Entity.AdminStudentsTimetable_Entity;

@Repository
public interface AdminStudentsTimetable_Repository extends JpaRepository<AdminStudentsTimetable_Entity, Long> {

    List<AdminStudentsTimetable_Entity> findByProgramName(String programName);
    
    List<AdminStudentsTimetable_Entity> findByDay(String day);
    
    List<AdminStudentsTimetable_Entity> findByProgramNameAndDay(String programName, String day);
	
}
