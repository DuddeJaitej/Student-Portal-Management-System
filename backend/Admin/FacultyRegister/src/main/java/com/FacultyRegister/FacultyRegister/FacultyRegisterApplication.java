package com.FacultyRegister.FacultyRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
@EnableJpaRepositories(basePackages = "com.FacultyRegister.FacultyRegister.Repository")
public class FacultyRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacultyRegisterApplication.class, args);
	}

}
