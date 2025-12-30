package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrDumpsterCreateDto implements Serializable {
    private Integer drDumpstersId;
    private String sourceDumpster;
    private String sizeDumpster;
    private String typeDumpster;
    private Integer quantity;
}