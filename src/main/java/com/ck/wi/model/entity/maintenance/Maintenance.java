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
@Table(name = "maintenances")
public class Maintenance implements Serializable {

    @Id
    @Column(name = "maintenances_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maintenancesId;

    @Column(name = "equipments_id")
    private Integer equipmentsId;

    @Column(name = "maintenance_type")
    private String maintenanceType;

    @Column(name = "measure_type")
    private String measureType;

    @Column(name = "frecuency")
    private Integer frecuency;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "maintenances_status")
    private String maintenancesStatus;
}
