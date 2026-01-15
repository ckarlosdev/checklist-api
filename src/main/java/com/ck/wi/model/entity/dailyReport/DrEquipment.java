package com.ck.wi.model.entity.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "dr_equipments")
public class DrEquipment implements Serializable {

    @Id
    @Column(name = "dr_equipments_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer drEquipmentsId;

    @Column(name = "daily_report_id")
    private Integer dailyReportId;

    @Column(name = "equipments_id")
    private Integer equipmentsId;

    @Column(name = "employees_id")
    private String employeesId;

    @Column(name = "operator")
    private String operator;

    @Column(name = "type")
    private String type;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "initial_hour")
    private String initialHour;

    @Column(name = "new_hour")
    private String newHour;

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
