package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.EquipmentIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentIssueDao extends CrudRepository<EquipmentIssue, Integer> {

    List<EquipmentIssue> findByFlowAndIssueStatus(String flow, String status);

    List<EquipmentIssue> findByIssueStatus(String issueStatus);

    @EntityGraph(attributePaths = {"equipment"})
    Page<EquipmentIssue> findByFlow(String flow, Pageable pageable);

    @EntityGraph(attributePaths = {"equipment"})
    Page<EquipmentIssue> findAll(Pageable pageable);


    @EntityGraph(attributePaths = {"equipment"})
    @Query("SELECT i FROM EquipmentIssue i WHERE " +
            "(:flow IS NULL OR i.flow = :flow) AND " +
            "(:search IS NULL OR " +
            " LOWER(i.equipment.number) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            " LOWER(i.reportedBy) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:priority IS NULL OR i.priority = :priority) AND " +
            "(:type IS NULL OR i.type = :type)")
    Page<EquipmentIssue> findIssuesWithFilters(
            @Param("flow") String flow,
            @Param("search") String search,
            @Param("priority") String priority,
            @Param("type") String type,
            Pageable pageable
    );

}
