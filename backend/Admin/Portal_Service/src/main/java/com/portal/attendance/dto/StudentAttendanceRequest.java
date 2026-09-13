package com.portal.attendance.dto;

import com.portal.attendance.AttendanceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentAttendanceRequest(
    @NotBlank String studentName,
    @NotBlank String studentRegisterNumber,
    String studentEmail,
    @NotNull AttendanceStatus status
) {
}
