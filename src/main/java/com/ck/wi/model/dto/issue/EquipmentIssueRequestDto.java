package com.ck.wi.model.dto.issue;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EquipmentIssueRequestDto {
    private Integer equipmentsIssuesId;
    private String equipmentNumber;
    private String equipmentName;
    private String flow;
    private String reportedBy;
    private LocalDate reportedDate;
    private String priorityIssue;
    private String typeIssue;
    private String descriptionIssue;
    private String details;
    private String createdBy;
    private String updatedBy;
}
