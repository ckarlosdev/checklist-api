package com.ck.wi.model.entity.dailyReport;

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
@Entity
@Table(name = "dr_employees")
public class DrEmployee implements Serializable {

    @Id
    @Column(name = "dr_employees_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drEmployeesId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "employees_id")
    private String employeesId;

    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;

    @Column(name = "in_hour")
    private LocalTime inHour;

    @Column(name = "out_hour")
    private LocalTime outHour;

    @Column(name = "lunch")
    private String lunch;

    @Column(name = "ppe")
    private String ppe;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "status")
    private String status;

}
