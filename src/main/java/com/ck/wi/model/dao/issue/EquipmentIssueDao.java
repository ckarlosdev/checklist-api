package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.EquipmentIssue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentIssueDao extends CrudRepository<EquipmentIssue, Integer> {

    List<EquipmentIssue> findByFlowAndIssueStatus(String flow, String status);

}
