package com.portal.attendance;

import com.portal.attendance.dto.AttendanceBatchRequest;
import com.portal.attendance.dto.AttendanceSummaryResponse;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal/attendance")
@CrossOrigin("*")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping("/mark")
    public List<AttendanceRecord> markAttendance(@Valid @RequestBody AttendanceBatchRequest request) {
        return service.markAttendance(request);
    }

    @GetMapping("/student/{registerNumber}/summary")
    public List<AttendanceSummaryResponse> getStudentSummary(@PathVariable String registerNumber) {
        return service.getStudentSummary(registerNumber);
    }

    @GetMapping("/student/{registerNumber}/records")
    public List<AttendanceRecord> getStudentRecords(@PathVariable String registerNumber) {
        return service.getStudentRecords(registerNumber);
    }

    @GetMapping("/faculty")
    public List<AttendanceRecord> getFacultyAttendance(
        @RequestParam String facultyRegisterNumber,
        @RequestParam String section,
        @RequestParam String courseName,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return service.getFacultyAttendance(facultyRegisterNumber, section, courseName, date);
    }
}
