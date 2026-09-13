package com.StudentRegister.StudentRegister.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StudentRegister.StudentRegister.Entity.StudentSignUp;

@Repository
public interface StudentSignUpRepository extends JpaRepository<StudentSignUp, String> {

    Optional<StudentSignUp> findByUsername(String username);

    Optional<StudentSignUp> findByEmail(String email);

    Optional<StudentSignUp> findByRegisterNo(String registerNo);

    boolean existsByEmail(String email);

    boolean existsByRegisterNo(String registerNo);
}
