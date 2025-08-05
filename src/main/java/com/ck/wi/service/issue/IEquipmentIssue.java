package com.ck.wi.service.issue;

import com.ck.wi.model.dto.issue.EquipmentIssueDto;
import com.ck.wi.model.entity.Issue.EquipmentIssue;

import java.util.List;

public interface IEquipmentIssue {

    EquipmentIssue save(EquipmentIssueDto equipmentIssueDto);

    EquipmentIssue update(EquipmentIssueDto equipmentIssueDto);

    EquipmentIssue findById(Integer equipmentIssueId);

    List<EquipmentIssue> findAll();

    List<EquipmentIssue> findByFlow(String flow);
}
