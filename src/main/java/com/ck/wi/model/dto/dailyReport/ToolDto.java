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
public class ToolDto implements Serializable {
    private Integer drToolId;
    private Integer dailyReportId;
    private Integer qty;
    private String name;
    private String other;
    private String type;
    private String comments;
    private String status;
}
