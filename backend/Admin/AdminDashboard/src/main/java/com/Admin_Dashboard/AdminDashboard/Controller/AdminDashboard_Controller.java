package com.Admin_Dashboard.AdminDashboard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Dashboard.AdminDashboard.Entity.AdminDashboard_Entity;
import com.Admin_Dashboard.AdminDashboard.Service.AdminDashboard_Service;

@RestController
@RequestMapping("/admin/AdminDashboard")
@CrossOrigin("*")
public class AdminDashboard_Controller {

	@Autowired
	private AdminDashboard_Service service;
	
	@GetMapping("Get-Admin_Dashboard")
	public ResponseEntity<AdminDashboard_Entity> getDashboardStats() {
        AdminDashboard_Entity stats = service.getDashboardStats();
        return ResponseEntity.ok(stats);
    }
	
}
