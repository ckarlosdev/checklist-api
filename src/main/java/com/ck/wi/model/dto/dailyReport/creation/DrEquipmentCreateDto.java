package com.ck.wi.model.dto.dailyReport.creation;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DrEquipmentCreateDto implements Serializable {
    private Integer drEquipmentsId;
    private Integer equipmentsId;
    private Integer employeesId;
    private String type;
    private String initialHour;
    private String newHour;
}
