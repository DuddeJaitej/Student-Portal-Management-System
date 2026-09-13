package com.FacultyRegister.FacultyRegister.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FacultyRegister.FacultyRegister.Entity.FacultyRegister_Entity;

@Repository
public interface FacultyRegister_Repository extends JpaRepository<FacultyRegister_Entity, Long> {
	Optional<FacultyRegister_Entity> findByName(String name);
	Optional<FacultyRegister_Entity> findByRegisterNumber(String registerNumber);
	boolean existsByName(String name);
	boolean existsByRegisterNumber(String registerNumber);
}
