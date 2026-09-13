package com.StudentRegister.StudentRegister.Controller;

import com.StudentRegister.StudentRegister.Entity.StudentSignUp;
import com.StudentRegister.StudentRegister.JwtUtil.StudentRegister_JwtUtil;
import com.StudentRegister.StudentRegister.Repository.StudentSignUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class StudentSignUpController {

    @Autowired
    private StudentSignUpRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRegister_JwtUtil jwtUtil;

    // ──────────────────────── SIGN UP ────────────────────────
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody Map<String, String> body) {
        Map<String, String> response = new HashMap<>();

        String email      = body.getOrDefault("email", "").trim();
        String username   = body.getOrDefault("username", "").trim();
        String registerNo = body.getOrDefault("registerNo", "").trim();
        String password   = body.getOrDefault("password", "").trim();

        // Basic validation
        if (email.isEmpty() || username.isEmpty() || registerNo.isEmpty() || password.isEmpty()) {
            response.put("error", "All fields (email, username, registerNo, password) are required.");
            return ResponseEntity.badRequest().body(response);
        }

        // Duplicate checks
        if (repository.existsByEmail(email)) {
            response.put("error", "An account with this email already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        if (repository.existsByRegisterNo(registerNo)) {
            response.put("error", "An account with this register number already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Persist
        StudentSignUp student = new StudentSignUp();
        student.setEmail(email);
        student.setUsername(username);
        student.setRegisterNo(registerNo);
        student.setPassword(passwordEncoder.encode(password));
        student.setRole("STUDENT");
        repository.save(student);

        response.put("message", "Registration successful. You can now log in.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────── LOGIN ────────────────────────
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> body) {
        Map<String, String> response = new HashMap<>();

        String identifier = body.getOrDefault("identifier", "").trim(); // email OR registerNo
        String password   = body.getOrDefault("password", "").trim();

        if (identifier.isEmpty() || password.isEmpty()) {
            response.put("error", "Identifier (email/registerNo) and password are required.");
            return ResponseEntity.badRequest().body(response);
        }

        // Try email first, then register number
        Optional<StudentSignUp> studentOpt = repository.findByEmail(identifier);
        if (studentOpt.isEmpty()) {
            studentOpt = repository.findByRegisterNo(identifier);
        }

        if (studentOpt.isEmpty()) {
            response.put("error", "No account found with the provided email or register number.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        StudentSignUp student = studentOpt.get();

        if (!passwordEncoder.matches(password, student.getPassword())) {
            response.put("error", "Invalid password. Please try again.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = jwtUtil.generateToken(
                student.getEmail(),
                student.getRegisterNo(),
                student.getUsername()
        );

        response.put("token", token);
        response.put("email", student.getEmail());
        response.put("username", student.getUsername());
        response.put("registerNo", student.getRegisterNo());
        response.put("role", student.getRole());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> updateProfile(@RequestBody Map<String, String> body) {
        String currentRegisterNo = body.getOrDefault("currentRegisterNo", "").trim();
        String username = body.getOrDefault("username", "").trim();
        String email = body.getOrDefault("email", "").trim();
        String registerNo = body.getOrDefault("registerNo", "").trim();

        Optional<StudentSignUp> existing = repository.findByRegisterNo(currentRegisterNo);
        if (existing.isEmpty() || username.isEmpty() || email.isEmpty() || registerNo.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Valid profile fields are required."));
        }

        StudentSignUp student = existing.get();
        if (!registerNo.equalsIgnoreCase(currentRegisterNo)
                && repository.existsByRegisterNo(registerNo)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "That register number is already in use."));
        }
        if (!email.equalsIgnoreCase(student.getEmail())) {
            String encodedPassword = student.getPassword();
            String role = student.getRole();
            repository.delete(student);
            repository.flush();

            StudentSignUp replacement = new StudentSignUp();
            replacement.setEmail(email);
            replacement.setUsername(username);
            replacement.setRegisterNo(registerNo);
            replacement.setPassword(encodedPassword);
            replacement.setRole(role);
            repository.save(replacement);
        } else {
            student.setUsername(username);
            student.setRegisterNo(registerNo);
            repository.save(student);
        }
        return ResponseEntity.ok(Map.of("message", "Profile updated successfully."));
    }

    // ──────────────────────── VERIFY TOKEN ────────────────────────
    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Map<String, String> response = new HashMap<>();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("error", "No token provided.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            response.put("error", "Token is invalid or expired.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        response.put("email", jwtUtil.extractEmail(token));
        response.put("registerNo", jwtUtil.extractRegisterNo(token));
        response.put("username", jwtUtil.extractUsername(token));
        response.put("role", "STUDENT");
        return ResponseEntity.ok(response);
    }
}
