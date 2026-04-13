package com.ck.wi.model.dto.dashboard;

import java.time.LocalDate;
import java.time.LocalTime;

public record CalendarEventDTO(
        Long id,
        Long jobsId,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        ReportStatusDTO reports
) {
    // Record interno para los indicadores
    public record ReportStatusDTO(
            Long daily,
            Long hazard,
            Long silica,
            Long checklist,
            Long demo
    ) {}
}
