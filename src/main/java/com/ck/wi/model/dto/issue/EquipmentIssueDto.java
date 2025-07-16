package com.ck.wi.model.dto.issue;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Equipment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EquipmentIssueDto implements Serializable {

    private Integer equipmentsIssuesId;
    private Integer checklistsId;
    private Integer equipmentsId;
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
