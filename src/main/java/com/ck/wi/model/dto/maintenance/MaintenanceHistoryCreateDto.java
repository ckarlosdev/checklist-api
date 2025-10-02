package com.ck.wi.model.dto.maintenance;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MaintenanceHistoryCreateDto implements Serializable {
    private Integer maintenancesHistoryId;
    private Integer maintenancesId;
    private LocalDate maintenanceDate;
    private String employee;
    private String odometer;
    private String comments;
    private String createdBy;
}
