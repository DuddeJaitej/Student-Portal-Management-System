package com.Faculty_Home.FacultyHome.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Faculty_Home.FacultyHome.Entity.FacultyHome_Entity;

@Repository
public interface FacultyHome_Repository extends JpaRepository<FacultyHome_Entity, Long> {

    List<FacultyHome_Entity> findByRegistrationNumber(String registrationNumber);
}