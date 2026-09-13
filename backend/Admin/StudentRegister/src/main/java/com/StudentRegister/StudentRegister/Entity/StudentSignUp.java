package com.StudentRegister.StudentRegister.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Student_SignUp")
public class StudentSignUp {

    @Id
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "username", unique = true, length = 100)
    private String username;

    @Column(name = "registerno", unique = true, length = 100)
    private String registerNo;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "role", length = 50)
    private String role = "STUDENT";

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
