package com.Admin_Dashboard.AdminDashboard.Service;

import com.Admin_Dashboard.AdminDashboard.Entity.AdminDashboard_Entity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminDashboard_Service {

    @Value("${students.service.url}")
    private String studentsUrl;

    @Value("${faculty.service.url}")
    private String facultyUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public AdminDashboard_Entity getDashboardStats() {
        try {
            Long totalStudents = getTotalCount(studentsUrl + "/Admin/Admin-Students/count");
            Long totalFaculty = getTotalCount(facultyUrl + "/Admin/Admin_Faculty/count");

            Map<String, Long> studentsByProgram = getCountBySection(studentsUrl + "/Admin/Admin-Students/by-section");
            Map<String, Long> facultyByProgram = getCountBySection(facultyUrl + "/Admin/Admin_Faculty/by-section");

            return new AdminDashboard_Entity(totalStudents, totalFaculty, studentsByProgram, facultyByProgram);

        } catch (Exception e) {
            e.printStackTrace();
            return new AdminDashboard_Entity(0L, 0L, new HashMap<>(), new HashMap<>());
        }
    }

    private Long getTotalCount(String url) {
        try {
            Long count = restTemplate.getForObject(url, Long.class);
            return count != null ? count : 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    private Map<String, Long> getCountBySection(String url) {
        try {
            ResponseEntity<Map<String, Long>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Long>>() {}
            );
            return response.getBody() != null ? response.getBody() : new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}