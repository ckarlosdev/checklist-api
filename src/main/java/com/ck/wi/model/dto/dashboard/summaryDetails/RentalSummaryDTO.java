package com.ck.wi.model.dto.dashboard.summaryDetails;

import java.time.LocalDate;
import java.util.Date;

public record RentalSummaryDTO(
        LocalDate date,
        String equipmentType,
        String company,
        String equipmentName
) {
}
