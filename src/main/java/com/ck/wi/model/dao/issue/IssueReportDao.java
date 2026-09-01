package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.IssueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueReportDao extends JpaRepository<IssueReport, Long> {
    List<IssueReport> findByEquipmentId(Integer equipmentId);
}
