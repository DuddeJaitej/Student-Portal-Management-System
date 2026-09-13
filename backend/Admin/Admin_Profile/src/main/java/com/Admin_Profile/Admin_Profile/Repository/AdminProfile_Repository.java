package com.Admin_Profile.Admin_Profile.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admin_Profile.Admin_Profile.Entity.AdminProfile_Entity;

@Repository
public interface AdminProfile_Repository extends JpaRepository<AdminProfile_Entity, Long> {

    Optional<AdminProfile_Entity> findByRegNo(String regNo);
}

