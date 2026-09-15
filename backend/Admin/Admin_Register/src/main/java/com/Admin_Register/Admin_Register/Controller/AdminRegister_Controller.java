package com.Admin_Register.Admin_Register.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Admin_Register.Admin_Register.Entity.AdminRegister_Entity;
import com.Admin_Register.Admin_Register.JwtUtil.AdminRegister_JwtUtil;
import com.Admin_Register.Admin_Register.Repository.AdminRegister_Repository;

@RestController
@RequestMapping("/Admin/Admin_Register")
@CrossOrigin(origins = "*")
public class AdminRegister_Controller {

	@Autowired
	private AdminRegister_Repository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AdminRegister_JwtUtil jwtUtil;
	
	@PostMapping("/AdminRegister")
	public ResponseEntity<String> register(@RequestBody AdminRegister_Entity adminRegister) {
		if (adminRegister == null || isBlank(adminRegister.getName()) || isBlank(adminRegister.getPassword())) {
			return ResponseEntity.badRequest().body("Admin name and password are required");
		}
		adminRegister.setPassword(encoder.encode(adminRegister.getPassword()));
		repo.save(adminRegister);
		return ResponseEntity.ok("Admin Registered Successfully");
	}
	
	@PostMapping("/AdminLogin")
	public ResponseEntity<String> login(@RequestBody AdminRegister_Entity adminLogin) {
	    try {
	        if (adminLogin == null || isBlank(adminLogin.getName()) || isBlank(adminLogin.getPassword())) {
	            return ResponseEntity.badRequest().body("Admin name and password are required");
	        }
	        AdminRegister_Entity dbAdmin = repo.findByName(adminLogin.getName())
	                .orElseThrow(() -> new RuntimeException("User not Found"));

	        if (!encoder.matches(adminLogin.getPassword(), dbAdmin.getPassword())) {
	            return ResponseEntity.badRequest().body("Invalid Password");
	        }

	        String token = jwtUtil.generateToken(adminLogin.getName());

	        System.out.println("✅ Login successful for: " + adminLogin.getName());
	        System.out.println("Token generated: " + token.substring(0, 50) + "...");

	        return ResponseEntity.ok(token);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
	    }
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
	
}
