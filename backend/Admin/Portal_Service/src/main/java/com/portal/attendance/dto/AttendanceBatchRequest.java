package com.portal.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record AttendanceBatchRequest(
    String courseCode,
    @NotBlank String courseName,
    @NotBlank String section,
    @NotBlank String facultyName,
    @NotBlank String facultyRegisterNumber,
    @NotNull LocalDate attendanceDate,
    @NotEmpty List<@Valid StudentAttendanceRequest> students
) {
}
