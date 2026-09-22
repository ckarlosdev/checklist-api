package com.ck.wi.model.dto.dailyReport.dashboard;

import java.util.List;

public record DailyReportGroupDto(
        String reportDate,
        String foreman,
        Double dailyTotalHours,
        List<EmployeeDetailDto> employees
) {
}
