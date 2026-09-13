package com.Faculty_Home.FacultyHome.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Faculty_Home.FacultyHome.Entity.FacultyHome_Entity;
import com.Faculty_Home.FacultyHome.JwtUtil.FacultyHome_Jwtutil;
import com.Faculty_Home.FacultyHome.Service.FacultyHome_Service;

@RestController
@RequestMapping("/Faculty_Home")
@CrossOrigin("*")
public class FacultyHome_Controll {

    @Autowired
    private FacultyHome_Service service;

    @Autowired
    private FacultyHome_Jwtutil jwtUtil;

    @GetMapping("/getCourses")
    public List<FacultyHome_Entity> getMyCourses(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "").trim();
        String registerNumber = jwtUtil.extractRegisterNumber(token);

        return service.getCoursesByRegistrationNumber(registerNumber);
    }
}