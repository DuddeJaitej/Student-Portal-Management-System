-- Repeatable, non-destructive setup for the faculty service.
CREATE DATABASE IF NOT EXISTS Faculty_Service;
USE Faculty_Service;

CREATE TABLE IF NOT EXISTS Faculty_SignUp (
    facultyname VARCHAR(100) NOT NULL,
    facultyid VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'FACULTY', 'ADMIN') NOT NULL DEFAULT 'FACULTY',
    PRIMARY KEY (facultyid)
);

