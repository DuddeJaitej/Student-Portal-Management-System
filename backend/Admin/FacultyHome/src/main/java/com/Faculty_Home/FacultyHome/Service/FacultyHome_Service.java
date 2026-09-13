package com.Faculty_Home.FacultyHome.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Faculty_Home.FacultyHome.Entity.FacultyHome_Entity;
import com.Faculty_Home.FacultyHome.Repository.FacultyHome_Repository;

@Service
public class FacultyHome_Service {

    @Autowired
    private FacultyHome_Repository repo;

    public List<FacultyHome_Entity> getCoursesByRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            return List.of();
        }
        return repo.findByRegistrationNumber(registrationNumber.trim());
    }
}