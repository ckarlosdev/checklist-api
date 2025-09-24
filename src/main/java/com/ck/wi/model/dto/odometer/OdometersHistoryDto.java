package com.ck.wi.model.dto.odometer;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OdometersHistoryDto implements Serializable {

    private Integer odometersHistoryId;
    private Integer odometersId;
    private Double previousLecture;
    private Double newLecture;
    private String reportedBy;
    private LocalDate reportedDate;
    private String createdBy;

}
