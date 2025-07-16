package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.model.entity.Issue.IssuesHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuesHistoryDao extends CrudRepository<IssuesHistory, Integer> {

    List<IssuesHistory> findByEquipmentIssue(EquipmentIssue equipmentIssue);

}
