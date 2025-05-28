package com.ck.wi.model.dto.dailyReport;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrEquipmentDto implements Serializable {
    private Integer drEquipmentsId;
    private Integer dailyReportId;
    private Integer equipmentsId;
    private String employeesId;
    private String operator;
    private String type;
    private String number;
    private String name;
    private String serialNumber;
    private String initialHour;
    private String newHour;
    private String status;
}
