package com.ck.wi.model.dto.odometer;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

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
    private Date reportedDate;
    private String createdBy;

}
