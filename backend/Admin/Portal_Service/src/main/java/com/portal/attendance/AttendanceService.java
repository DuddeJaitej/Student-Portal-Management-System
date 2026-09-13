package com.portal.attendance;

import com.portal.attendance.dto.AttendanceBatchRequest;
import com.portal.attendance.dto.AttendanceSummaryResponse;
import com.portal.attendance.dto.StudentAttendanceRequest;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AttendanceService {

    private final AttendanceRecordRepository repository;

    public AttendanceService(AttendanceRecordRepository repository) {
        this.repository = repository;
    }

    public List<AttendanceRecord> markAttendance(AttendanceBatchRequest request) {
        if (request.attendanceDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendance cannot be marked for a future date.");
        }

        List<AttendanceRecord> records = request.students().stream()
            .map(student -> toRecord(request, student))
            .toList();

        return repository.saveAll(records);
    }

    public List<AttendanceSummaryResponse> getStudentSummary(String registerNumber) {
        List<AttendanceRecord> records = getStudentRecords(registerNumber);
        Map<String, List<AttendanceRecord>> byCourse = records.stream()
            .collect(Collectors.groupingBy(AttendanceRecord::getCourseName));

        return byCourse.entrySet().stream()
            .map(entry -> {
                long total = entry.getValue().size();
                long present = entry.getValue().stream()
                    .filter(record -> record.getStatus() == AttendanceStatus.PRESENT)
                    .count();
                long absent = total - present;
                double percentage = total == 0 ? 0 : Math.round((present * 10000.0) / total) / 100.0;
                return new AttendanceSummaryResponse(entry.getKey(), total, present, absent, percentage);
            })
            .sorted(Comparator.comparing(AttendanceSummaryResponse::courseName))
            .toList();
    }

    public List<AttendanceRecord> getStudentRecords(String registerNumber) {
        if (registerNumber == null || registerNumber.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student register number is required.");
        }
        return repository.findByStudentRegisterNumberOrderByAttendanceDateDesc(registerNumber.trim());
    }

    public List<AttendanceRecord> getFacultyAttendance(String facultyRegisterNumber, String section, String courseName, LocalDate date) {
        if (date != null) {
            return repository.findByFacultyRegisterNumberAndSectionAndCourseNameAndAttendanceDate(
                facultyRegisterNumber.trim(),
                section.trim(),
                courseName.trim(),
                date
            );
        }
        return repository.findByFacultyRegisterNumberAndSectionAndCourseNameOrderByAttendanceDateDesc(
            facultyRegisterNumber.trim(),
            section.trim(),
            courseName.trim()
        );
    }

    private AttendanceRecord toRecord(AttendanceBatchRequest request, StudentAttendanceRequest student) {
        AttendanceRecord record = repository
            .findByStudentRegisterNumberAndCourseNameAndAttendanceDate(
                student.studentRegisterNumber().trim(),
                request.courseName().trim(),
                request.attendanceDate()
            )
            .orElseGet(AttendanceRecord::new);

        record.setCourseCode(trimOrNull(request.courseCode()));
        record.setCourseName(request.courseName().trim());
        record.setSection(request.section().trim());
        record.setFacultyName(request.facultyName().trim());
        record.setFacultyRegisterNumber(request.facultyRegisterNumber().trim());
        record.setStudentName(student.studentName().trim());
        record.setStudentRegisterNumber(student.studentRegisterNumber().trim());
        record.setStudentEmail(trimOrNull(student.studentEmail()));
        record.setAttendanceDate(request.attendanceDate());
        record.setStatus(student.status());
        record.markUpdated();
        return record;
    }

    private String trimOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
