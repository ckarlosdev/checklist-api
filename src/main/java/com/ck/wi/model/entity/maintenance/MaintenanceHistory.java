package com.ck.wi.model.entity.maintenance;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "maintenances_history")
public class MaintenanceHistory implements Serializable {

    @Id
    @Column(name = "maintenances_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maintenancesHistoryId;

    @Column(name = "maintenances_id")
    private Integer maintenancesId;

    @Column(name = "maintenance_date")
    private LocalDate maintenanceDate;

    @Column(name = "employee")
    private String employee;

    @Column(name = "odometer")
    private String odometer;

    @Column(name = "comments")
    private String comments;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "me_status")
    private String meStatus;
}
