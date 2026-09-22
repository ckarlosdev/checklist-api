package com.ck.wi.model.dto.dashboard.summaryDetails;

import java.time.LocalDate;
import java.util.Date;

public record PhotoSummaryDTO(
        LocalDate date,
        String type,
        String folderId,
        Long totalQuantity
) {
}
