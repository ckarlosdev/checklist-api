package com.ck.wi.model.dto.dashboard.summaryDetails;

import java.time.LocalDate;
import java.util.Date;

public record DumpsterSummaryDTO(
        LocalDate date,
        String sourceDumpster,
        String sizeDumpster,
        String typeDumpster,
        Long totalQuantity
) {
}
