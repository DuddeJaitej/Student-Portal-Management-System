package com.Admin_Students_Timetable.Admin_Students_Timetable.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Students_Timetable.Admin_Students_Timetable.Entity.AdminStudentsTimetable_Entity;
import com.Admin_Students_Timetable.Admin_Students_Timetable.Service.AdminStudentsTimetable_Service;

@RestController
@RequestMapping("/Admin/Admin_Students_Timetable")
@CrossOrigin("*")
public class AdminStudentsTimetable_Controller {
	@Autowired
	AdminStudentsTimetable_Service service;
		
	@PostMapping("/create_AdminStudentsTimetable")
	public ResponseEntity<?> createTimetable(@RequestBody List<AdminStudentsTimetable_Entity> timetableList) {
	    try {
	        if (timetableList == null || timetableList.isEmpty()) {
	            return ResponseEntity.badRequest().body("Timetable list is empty");
	        }
	        
	        List<AdminStudentsTimetable_Entity> saved = service.createTimetable(timetableList);
	        return ResponseEntity.ok(saved);
	        
	    } catch (Exception e) {
	        e.printStackTrace(); // This will show the real error in console
	        return ResponseEntity.internalServerError()
	                .body("Failed to save timetable: " + e.getMessage());
	    }
	}	
	
	@GetMapping("/by-program/{programName}")
	public ResponseEntity<List<AdminStudentsTimetable_Entity>> getTimetableByProgram(@PathVariable String programName){
		List<AdminStudentsTimetable_Entity> timetable = service.getTimetableByProgram(programName);
		return ResponseEntity.ok(timetable);
	}
	
	@GetMapping("/by-day/{day}")
	public ResponseEntity<List<AdminStudentsTimetable_Entity>> getTimetableByDay(@PathVariable String day){
		List<AdminStudentsTimetable_Entity> timetable = service.getTimetableByDay(day);
		return ResponseEntity.ok(timetable);
	}
	
	@GetMapping("/by-program-and-day")
	public ResponseEntity<List<AdminStudentsTimetable_Entity>> getTimetableByProgramAndDay(@RequestParam String programName, @RequestParam String day){
		List<AdminStudentsTimetable_Entity> timetable = service.getTimetableByProgramAndDay(programName, day);
		return ResponseEntity.ok(timetable);
	}
}
