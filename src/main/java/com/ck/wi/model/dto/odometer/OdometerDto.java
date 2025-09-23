package com.ck.wi.model.dto.odometer;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OdometerDto implements Serializable {

    private Integer odometersId;
    private Integer equipmentsId;
    private Double odometer;
    private OdometersHistoryDto odometersHistory;
}
