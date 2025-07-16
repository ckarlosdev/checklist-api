package com.ck.wi.model.dto.issue;

import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class IssuesHistoryDto implements Serializable {

    private Integer issuesHistoryId;
    private Integer equipmentsIssuesId;
    private String lastFlow;
    private String newFlow;
    private String comments;
    private String createdBy;
    private String status;

}
