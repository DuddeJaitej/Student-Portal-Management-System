package com.StudentRegister.StudentRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
@EnableJpaRepositories(basePackages = "com.StudentRegister.StudentRegister.Repository")
public class StudentRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentRegisterApplication.class, args);
	}

}
