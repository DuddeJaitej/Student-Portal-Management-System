package com.portal.attendance.dto;

public record AttendanceSummaryResponse(
    String courseName,
    long totalClasses,
    long presentClasses,
    long absentClasses,
    double percentage
) {
}
