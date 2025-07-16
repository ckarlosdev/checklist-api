package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.EquipmentIssue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentIssueDao extends CrudRepository<EquipmentIssue, Integer> {

}
