package com.ck.wi.service.issue;

import com.ck.wi.model.dto.issue.IssuesHistoryDto;
import com.ck.wi.model.entity.Issue.IssuesHistory;

import java.util.List;

public interface IIssuesHistory {

    IssuesHistory save(IssuesHistoryDto issuesHistoryDto);

    IssuesHistory update(IssuesHistoryDto issuesHistoryDto);

    List<IssuesHistory> findByIssueId(Integer issueId);

}
