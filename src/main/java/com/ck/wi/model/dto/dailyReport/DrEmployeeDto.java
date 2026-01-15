package com.ck.wi.model.dto.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrEmployeeDto implements Serializable {
    private Integer drEmployeesId;
    private Integer dailyReportId;
    private String employeesId;
    private String name;
    private String title;
    private LocalTime inHour;
    private LocalTime outHour;
    private String lunch;
    private String ppe;
    private String comment;
    private String status;
}
