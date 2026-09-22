package com.ck.wi.model.dto.dailyReport.dashboard;

public record EquipmentSummaryDto(
        String equipmentName,
        Long totalReports,
        Long totalEquipmentEntries,
        Double totalHours
) {
}
