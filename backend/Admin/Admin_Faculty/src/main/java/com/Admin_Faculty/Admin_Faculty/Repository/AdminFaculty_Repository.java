package com.Admin_Faculty.Admin_Faculty.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Admin_Faculty.Admin_Faculty.Entity.AdminFaculty_Entity;

@Repository
public interface AdminFaculty_Repository extends JpaRepository<AdminFaculty_Entity, Long> {

	Optional<AdminFaculty_Entity> findByRegisterNoIgnoreCase(String registerNo);
	
	@Query("SELECT f.section, COUNT(f) FROM AdminFaculty_Entity f GROUP BY f.section")
	List<Object[]> countBySection();
	
	@Query("SELECT f FROM AdminFaculty_Entity f " +
	           "WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
	           "   OR LOWER(f.registerNo) LIKE LOWER(CONCAT('%', :query, '%'))")
	    List<AdminFaculty_Entity> searchFaculty(@Param("query") String query);
	
}
