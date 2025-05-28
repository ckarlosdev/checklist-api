package com.ck.wi.model.dto.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

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
    private Time inHour;
    private Time outHour;
    private String lunch;
    private String ppe;
    private String comment;
    private String status;
}
