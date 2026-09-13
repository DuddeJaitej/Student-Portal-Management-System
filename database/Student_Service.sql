CREATE DATABASE IF NOT EXISTS Student_Service;
use Student_Service;

CREATE TABLE IF NOT EXISTS Student_SignUp (
    username VARCHAR(51) UNIQUE,
    registerno VARCHAR(20) UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'STUDENT',
    PRIMARY KEY (email),
    UNIQUE KEY uq_student_username (username),
    UNIQUE KEY uq_student_registerno (registerno)
);
