package com.FacultyRegister.FacultyRegister.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.FacultyRegister.FacultyRegister.Entity.FacultyRegister_Entity;
import com.FacultyRegister.FacultyRegister.JwtUtil.FacultyRegister_JwtUtil;
import com.FacultyRegister.FacultyRegister.Repository.FacultyRegister_Repository;

@RestController
@RequestMapping("/Faculty/Faculty_Login")
@CrossOrigin(origins = "*")
public class FacultyRegister_Controller {

    @Autowired
    private FacultyRegister_Repository repo;

    @Autowired
    private FacultyRegister_JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/FacultyRegister")
    public ResponseEntity<String> register(@RequestBody FacultyRegister_Entity request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("Request body is required.");
        }

        String name = request.getName() == null ? "" : request.getName().trim();
        String registerNumber = request.getRegisterNumber() == null ? "" : request.getRegisterNumber().trim();
        String password = request.getPassword() == null ? "" : request.getPassword().trim();

        if (name.isEmpty()) {
            return ResponseEntity.badRequest().body("Faculty name is required");
        }
        if (registerNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Register number is required");
        }
        if (password.isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }
        if (repo.existsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Faculty name already exists");
        }
        if (repo.existsByRegisterNumber(registerNumber)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Register number already exists");
        }

        FacultyRegister_Entity faculty = new FacultyRegister_Entity();
        faculty.setName(name);
        faculty.setRegisterNumber(registerNumber);
        faculty.setPassword(passwordEncoder.encode(password));
        repo.save(faculty);

        return ResponseEntity.status(HttpStatus.CREATED).body("Faculty registered successfully");
    }

    @PostMapping("/FacultyLogin")
    public ResponseEntity<String> login(@RequestBody FacultyRegister_Entity loginRequest) {
        try {
            if (loginRequest == null) {
                return ResponseEntity.badRequest().body("Login details are required");
            }
            String inputName = loginRequest.getName();
            String inputRegisterNo = loginRequest.getRegisterNumber();
            String inputPassword = loginRequest.getPassword();

            if (inputName == null || inputName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Faculty Name is required");
            }
            if (inputRegisterNo == null || inputRegisterNo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Register Number is required");
            }
            if (inputPassword == null || inputPassword.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            String name = inputName.trim();
            String regNo = inputRegisterNo.trim();

            FacultyRegister_Entity dbFaculty = repo.findByName(name)
                    .orElseThrow(() -> new RuntimeException("Faculty not found with name: " + name));

            if (!regNo.equalsIgnoreCase(dbFaculty.getRegisterNumber())) {
                return ResponseEntity.badRequest().body("Register Number does not match the faculty name");
            }
            if (!passwordEncoder.matches(inputPassword, dbFaculty.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid password");
            }

            String token = jwtUtil.generateToken(dbFaculty.getName(), dbFaculty.getRegisterNumber());
            System.out.println("✅ Login successful for: " + dbFaculty.getName() + " | Reg No: " + dbFaculty.getRegisterNumber());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }

    @PutMapping("/FacultyProfile")
    public ResponseEntity<String> updateProfile(@RequestBody Map<String, String> body) {
        String currentRegisterNo = body.getOrDefault("currentRegisterNo", "").trim();
        String name = body.getOrDefault("name", "").trim();
        String registerNumber = body.getOrDefault("registerNumber", "").trim();
        if (currentRegisterNo.isEmpty() || name.isEmpty() || registerNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Name and register number are required");
        }

        FacultyRegister_Entity faculty = repo.findByRegisterNumber(currentRegisterNo).orElse(null);
        if (faculty == null) return ResponseEntity.notFound().build();
        if (!registerNumber.equalsIgnoreCase(currentRegisterNo)
                && repo.existsByRegisterNumber(registerNumber)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Register number already exists");
        }
        faculty.setName(name);
        faculty.setRegisterNumber(registerNumber);
        repo.save(faculty);
        return ResponseEntity.ok("Profile updated successfully");
    }
}