package com.ck.wi.model.dto.dailyReport;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class DailyReportSummaryDto {
    private String type;
    private String value;
    private Long count;

    public DailyReportSummaryDto(
            String type,
            String value,
            Long count
    ){
        this.type = type;
        this.value = value;
        this.count = count;
    }
}
