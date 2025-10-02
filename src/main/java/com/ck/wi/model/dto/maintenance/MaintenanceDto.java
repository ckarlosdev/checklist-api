package com.ck.wi.model.dto.maintenance;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MaintenanceDto implements Serializable {

    private Integer maintenancesId;
    private Integer equipmentsId;
    private String maintenanceType;
    private String measureType;
    private Integer frecuency;
    private String description;
    private String createdBy;
    private MaintenanceHistoryDto maintenanceHistoryDto;
}
