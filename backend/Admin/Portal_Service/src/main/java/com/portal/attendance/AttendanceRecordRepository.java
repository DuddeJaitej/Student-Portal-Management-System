package com.portal.attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {

    Optional<AttendanceRecord> findByStudentRegisterNumberAndCourseNameAndAttendanceDate(
        String studentRegisterNumber,
        String courseName,
        LocalDate attendanceDate
    );

    List<AttendanceRecord> findByStudentRegisterNumberOrderByAttendanceDateDesc(String studentRegisterNumber);

    List<AttendanceRecord> findByFacultyRegisterNumberAndSectionAndCourseNameAndAttendanceDate(
        String facultyRegisterNumber,
        String section,
        String courseName,
        LocalDate attendanceDate
    );

    List<AttendanceRecord> findByFacultyRegisterNumberAndSectionAndCourseNameOrderByAttendanceDateDesc(
        String facultyRegisterNumber,
        String section,
        String courseName
    );
}
