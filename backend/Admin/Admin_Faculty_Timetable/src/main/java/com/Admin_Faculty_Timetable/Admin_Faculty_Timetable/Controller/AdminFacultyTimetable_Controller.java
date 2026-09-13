package com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Entity.AdminFacultyTimetable_Entity;
import com.Admin_Faculty_Timetable.Admin_Faculty_Timetable.Service.AdminFacultyTimetable_Service;

@RestController
@RequestMapping("/Admin/Admin_Faculty_Timetable")
@CrossOrigin("*")
public class AdminFacultyTimetable_Controller {

	@Autowired
	AdminFacultyTimetable_Service service;
		
	@PostMapping("/create_AdminFacultyTimetable")
	public ResponseEntity<?> createTimetable(@RequestBody List<AdminFacultyTimetable_Entity> timetableList) {
	    try {
	        if (timetableList == null || timetableList.isEmpty()) {
	            return ResponseEntity.badRequest().body("Timetable list is empty");
	        }
	        
	        List<AdminFacultyTimetable_Entity> saved = service.createTimetable(timetableList);
	        return ResponseEntity.ok(saved);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError()
	                .body("Failed to save timetable: " + e.getMessage());
	    }
	}
	
	@GetMapping("/getByFaculty")
	public ResponseEntity<List<AdminFacultyTimetable_Entity>> getTimetableFaculty(@RequestParam String facultyName){
		try {
			List<AdminFacultyTimetable_Entity> timetable = service.getTimetableByFaculty(facultyName);
			return ResponseEntity.ok(timetable);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
	}
	
}
