package com.ck.wi.model.dto.dailyReport.dashboard;

public record EmployeeSummaryDto(
        String employeeTitle,
        String employeeName,
        Long totalReports,
        Long totalEmployeeEntries,
        Double totalHours
) {
}
