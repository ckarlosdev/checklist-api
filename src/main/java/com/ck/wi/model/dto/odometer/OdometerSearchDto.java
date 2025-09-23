package com.ck.wi.model.dto.odometer;

import com.ck.wi.model.entity.Equipment;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OdometerSearchDto implements Serializable {
    private Integer odometersId;
    private Integer equipmentsId;
    private String equipmentName;
    private String equipmentNumber;
    private Double odometer;
}
