package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.model.entity.Issue.IssuesHistory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuesHistoryDao extends CrudRepository<IssuesHistory, Integer> {

    List<IssuesHistory> findByEquipmentIssue(EquipmentIssue equipmentIssue);

    @Modifying // Indica que esta consulta modificará datos
    @Query("UPDATE IssuesHistory ih SET ih.historyStatus = :status WHERE ih.equipmentIssue.equipmentsIssuesId = :equipmentIssueId")
    void updateHistoryStatusByEquipmentIssueId(@Param("equipmentIssueId") Integer equipmentIssueId, @Param("status") String status);

}
