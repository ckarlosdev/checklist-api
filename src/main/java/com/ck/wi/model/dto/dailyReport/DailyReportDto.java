package com.ck.wi.model.dto.dailyReport;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DailyReportDto implements Serializable {
    private Integer dailyReportId;
    private String number;
    private String address;
    private String name;
    private String workingFor;
    private LocalDate date;
    private String foreman;
    private String crew;
    private String description;
    private String manTotal;
    private String manHoursTotal;
    private String manOther;
    private String equipmentTotal;
    private String equipHoursTotal;
    private String equipmentOther;
    private String issues;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private String status;
}
