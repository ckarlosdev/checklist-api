package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrTotalsDto implements Serializable {
    private Integer days;
    private Double hours;
}
