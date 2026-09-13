package com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Entity.AdminFacultyTimetable_Entity;

@Repository
public interface AdminFacultyTimetable_Repository extends JpaRepository<AdminFacultyTimetable_Entity, Long> {

    List<AdminFacultyTimetable_Entity> findByProgramName(String programName);
    
    @Query("SELECT e FROM AdminFacultyTimetable_Entity e WHERE e.facultyName = :facultyName")
    List<AdminFacultyTimetable_Entity> findByFacultyName(@Param("facultyName") String facultyName);
}
