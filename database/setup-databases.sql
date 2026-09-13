-- Run this script once as a MySQL administrator.
-- Spring Boot/Hibernate creates and updates tables with ddl-auto=update.

CREATE DATABASE IF NOT EXISTS Admin_Service;
CREATE DATABASE IF NOT EXISTS AdminStudent_Course;
CREATE DATABASE IF NOT EXISTS AdminFaculty_Course;
CREATE DATABASE IF NOT EXISTS AdminStudents_Details;
CREATE DATABASE IF NOT EXISTS AdminFaculty_Details;
CREATE DATABASE IF NOT EXISTS AdminFaculty_Timetable;
CREATE DATABASE IF NOT EXISTS AdminStudents_Timetable;
CREATE DATABASE IF NOT EXISTS Student_Service;
CREATE DATABASE IF NOT EXISTS Faculty_Service;
CREATE DATABASE IF NOT EXISTS Portal_Service;

-- Verify the databases used by the services.
SHOW DATABASES;
