package com.ck.wi.model.dto.dailyReport;

import lombok.*;

@Data
@NoArgsConstructor
@ToString
public class DumpsterSummaryDto {
    private Long concrete;
    private Long metal;
    private Long cd;

    public DumpsterSummaryDto(
            Long concrete,
            Long metal,
            Long cd
    ){
        this.concrete = concrete;
        this.metal = metal;
        this.cd = cd;
    }
}
