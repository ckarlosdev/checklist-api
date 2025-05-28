package com.ck.wi.model.dto.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DumpsterDto implements Serializable {
    private Integer drDumpstersId;
    private Integer dailyReportId;
    private Integer concret40;
    private Integer concret35;
    private Integer concret30;
    private Integer concret20;
    private Integer concret12;
    private Integer metal40;
    private Integer metal35;
    private Integer metal30;
    private Integer metal20;
    private Integer metal12;
    private Integer cd40;
    private Integer cd35;
    private Integer cd30;
    private Integer cd20;
    private Integer cd12;
    private String status;
    private Integer concretQuad;
    private Integer concretSemi;
    private Integer concretGondola;
    private Integer metalQuad;
    private Integer metalSemi;
    private Integer metalGondola;
    private Integer cdQuad;
    private Integer cdSemi;
    private Integer cdGondola;
}
