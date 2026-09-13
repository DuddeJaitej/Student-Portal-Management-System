package com.AdminStudentCourse.Admin_Student_Course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.AdminStudentCourse", "Admin_Student"})
@EnableJpaRepositories(basePackages = {"Admin_Student.Repository"})
public class AdminStudentCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminStudentCourseApplication.class, args);
	}

}
