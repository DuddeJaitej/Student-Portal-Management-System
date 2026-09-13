package com.admin_Students.Admin_Students.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.admin_Students.Admin_Students.Entity.AdminStudents_Entity;

@Repository
public interface AdminStudents_Repository extends JpaRepository<AdminStudents_Entity, Long>{
	
	 	List<AdminStudents_Entity> findBySection(String section);

	    long countBySection(String section);

    @Query("SELECT s.section, COUNT(s) FROM AdminStudents_Entity s GROUP BY s.section")
    List<Object[]> countStudentsBySection();
    
    @Query("SELECT s FROM AdminStudents_Entity s " +
           "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "   OR LOWER(s.registerNo) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<AdminStudents_Entity> searchStudents(@Param("query") String query);

    Optional<AdminStudents_Entity> findByRegisterNo(String registerNo);
}
