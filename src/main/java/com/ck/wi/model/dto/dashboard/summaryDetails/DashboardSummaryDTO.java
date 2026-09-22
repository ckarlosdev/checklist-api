package com.ck.wi.model.dto.dashboard.summaryDetails;

import java.util.List;

public record DashboardSummaryDTO(
        Long jobId,
        List<ToolSummaryDTO> tools,
        List<DumpsterSummaryDTO> dumpsters,
        List<PhotoSummaryDTO> photos,
        List<RentalSummaryDTO> rentals
) {
}
