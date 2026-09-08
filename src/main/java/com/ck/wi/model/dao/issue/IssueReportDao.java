package com.ck.wi.model.dao.issue;

import com.ck.wi.model.entity.Issue.IssueReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueReportDao extends JpaRepository<IssueReport, Long> {
    List<IssueReport> findByEquipment_EquipmentsId(Integer equipmentId);

    @Override
    @EntityGraph(attributePaths = {"equipment"})
    Page<IssueReport> findAll(Pageable pageable);

    // Si necesitas filtrar por equipo en el futuro con paginación:
    @EntityGraph(attributePaths = {"equipment"})
    Page<IssueReport> findByEquipment_EquipmentsId(Integer equipmentId, Pageable pageable);
}
