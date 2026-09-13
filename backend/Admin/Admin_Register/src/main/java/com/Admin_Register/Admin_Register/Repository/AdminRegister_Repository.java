package com.Admin_Register.Admin_Register.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Admin_Register.Admin_Register.Entity.AdminRegister_Entity;

public interface AdminRegister_Repository extends JpaRepository<AdminRegister_Entity, Long> {
	Optional<AdminRegister_Entity> findByName(String name);
}
