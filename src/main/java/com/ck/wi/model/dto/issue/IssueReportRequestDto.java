package com.ck.wi.model.dto.issue;

import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
public class IssueReportRequestDto {
    private Integer equipmentId;
    private String reportedBy;
    private String priorityIssue;
    private String typeIssue;
    private String descriptionIssue;
}
