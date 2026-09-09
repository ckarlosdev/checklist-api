package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.IssueReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueReportDao extends JpaRepository<IssueReport, Long> {
    List<IssueReport> findByEquipment_EquipmentsId(Integer equipmentId);

    @Override
    @EntityGraph(attributePaths = {"equipment"})
    Page<IssueReport> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"equipment"})
    Page<IssueReport> findByEquipment_EquipmentsId(Integer equipmentId, Pageable pageable);

    @EntityGraph(attributePaths = {"equipment"})
    @Query("SELECT r FROM IssueReport r WHERE " +
            "(:search IS NULL OR " +
            " LOWER(r.equipment.number) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            " LOWER(r.reportedBy) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:priority IS NULL OR r.priorityIssue = :priority) AND " + // Corrección: priorityIssue
            "(:type IS NULL OR r.typeIssue = :type)")                     // Corrección: typeIssue
    Page<IssueReport> findActiveWithFilters(
            @Param("search") String search,
            @Param("priority") String priority,
            @Param("type") String type,
            Pageable pageable
    );
}
