package com.ck.wi.model.dto.dailyReport.dashboard;

public record EmployeeDetailDto(
        Long drEmployeesId,
        String employeeName,
        String employeeTitle,
        String inHour,
        String outHour,
        Boolean lunch,
        Double hoursWorked
) {
}
