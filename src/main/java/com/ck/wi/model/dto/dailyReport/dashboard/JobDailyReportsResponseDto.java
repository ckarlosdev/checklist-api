package com.ck.wi.model.dto.dailyReport.dashboard;

import java.util.List;

public record JobDailyReportsResponseDto(
        Long jobId,
        String jobNumber,
        String jobName,
        String startDate,
        String endDate,
        List<DailyReportGroupDto> reportsByDate
) {
}
