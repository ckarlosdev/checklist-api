package com.ck.wi.service.issue;

import com.ck.wi.model.dto.issue.IssueReportRequestDto;
import com.ck.wi.model.dto.issue.IssueReportResponseDto;

import java.util.List;

public interface IIssueReport {
    IssueReportResponseDto save(IssueReportRequestDto dto);

    void delete(Long id);

    List<IssueReportResponseDto> findByEquipment(Integer equipmentId);

    IssueReportResponseDto findById(Long id);
}
