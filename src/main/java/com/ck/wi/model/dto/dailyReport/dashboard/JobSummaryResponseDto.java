package com.ck.wi.model.dto.dailyReport.dashboard;

import java.util.List;

public record JobSummaryResponseDto(
        Long jobId,
        String jobNumber,
        String jobName,
        Double totalLaborHours,
        Double totalEquipmentHours,
        List<EmployeeSummaryDto> employeeSummaries,
        List<EquipmentSummaryDto> equipmentSummaries
) {
}
