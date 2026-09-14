package com.ck.wi.model.dto.issue;

import java.util.List;

public record UpdateIssueFlowRequest(
        List<Integer> issueIds,
        String newFlow
) {
}
