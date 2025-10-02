package com.ck.wi.model.dto.maintenance;

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
public class MaintenanceHistoryDto implements Serializable {
    private LocalDate maintenanceDate;
    private String employee;
    private String odometer;
    private String comments;
    private String createdBy;
}
