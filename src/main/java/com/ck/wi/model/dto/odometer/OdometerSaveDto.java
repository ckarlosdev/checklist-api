package com.ck.wi.model.dto.odometer;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OdometerSaveDto implements Serializable {

    private Integer equipmentsId;
    private Double newLecture;
    private OdometersHistoryDto odometersHistory;
    private Double previousLecture;
    private String reportedBy;
    private LocalDate reportedDate;
    private String createdBy;

}
