package com.ck.wi.model.dto.dashboard.summaryDetails;

import java.time.LocalDate;

public record ToolSummaryDTO(
        LocalDate date,
        String name,
        Long totalQuantity
) {
}
