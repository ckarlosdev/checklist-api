package com.ck.wi.model.entity.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "dr_rentals")
public class DrRental implements Serializable {
    @Id
    @Column(name = "dr_rentals_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drRentalsId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "employees_id")
    private Integer employeesId;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "equipment_name")
    private String equipmentName;

    @Column(name = "company")
    private String company;

    @Column(name = "equipment_number")
    private String equipmentNumber;

    @Column(name = "odometer")
    private Double odometer;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "rentals_status")
    private String rentalsStatus;
}
