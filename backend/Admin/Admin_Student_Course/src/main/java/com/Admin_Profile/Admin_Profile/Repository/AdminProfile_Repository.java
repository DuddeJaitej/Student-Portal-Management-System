package com.Admin_Profile.Admin_Profile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admin_Profile.Admin_Profile.Entity.AdminProfile_Entity;

@Repository
public interface AdminProfile_Repository extends JpaRepository<AdminProfile_Entity, Long> {

}
