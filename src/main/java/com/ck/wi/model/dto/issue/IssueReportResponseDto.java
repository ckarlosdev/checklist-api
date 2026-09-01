package com.ck.wi.model.dto.issue;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class IssueReportResponseDto {
    private Long id;
    private Integer equipmentId;
    private String reportedBy;
    private String priorityIssue;
    private String typeIssue;
    private String descriptionIssue;
    private LocalDateTime createdAt;
}
