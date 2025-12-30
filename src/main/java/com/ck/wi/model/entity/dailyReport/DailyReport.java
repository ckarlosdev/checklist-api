package com.ck.wi.model.entity.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import jakarta.persistence.*;
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
@Entity
@Table(name = "daily_reports")
public class DailyReport implements Serializable {
    @Id
    @Column(name = "daily_report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dailyReportId;

    @Column(name = "number")
    private String number;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "working_for")
    private String workingFor;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "foreman")
    private String foreman;

    @Column(name = "crew")
    private String crew;

    @Column(name = "description")
    private String description;

    @Column(name = "man_total")
    private String manTotal;

    @Column(name = "man_hours_total")
    private String manHoursTotal;

    @Column(name = "man_other")
    private String manOther;

    @Column(name = "equipment_total")
    private String equipmentTotal;

    @Column(name = "equip_hours_total")
    private String equipHoursTotal;

    @Column(name = "equipment_other")
    private String equipmentOther;

    @Column(name = "issues")
    private String issues;

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

    @Column(name = "token")
    private String token;
}
