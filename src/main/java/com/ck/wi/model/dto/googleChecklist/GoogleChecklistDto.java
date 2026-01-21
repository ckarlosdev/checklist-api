package com.ck.wi.model.dto.googleChecklist;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GoogleChecklistDto implements Serializable {
    private Integer googleChecklistsId;
    private Integer equipmentsGoogleChecklistsId;
    private String equipmentNumber;
    private String equipmentName;
    private String operator;
    private double odometer;
    private String oil;
    private String hydraulic;
    private String filter;
    private String radiator;
    private String track;
    private String attachment;
    private String leaking;
    private String diesel;
    private String clean;
    private String comment;
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;
    private String status;
    private String otherType;
}
