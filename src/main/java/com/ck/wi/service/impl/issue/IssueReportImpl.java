package com.ck.wi.service.impl.issue;

import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.issue.IssueReportDao;
import com.ck.wi.model.dto.issue.IssueReportRequestDto;
import com.ck.wi.model.dto.issue.IssueReportResponseDto;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.Issue.IssueReport;
import com.ck.wi.service.issue.IIssueReport;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueReportImpl implements IIssueReport {

    private final EquipmentDao equipmentDao;
    private final IssueReportDao issueReportDao;

    @Override
    @Transactional
    public IssueReportResponseDto save(IssueReportRequestDto dto){
        Equipment equipment = equipmentDao.findById(dto.getEquipmentId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with ID: " + dto.getEquipmentId()));

        // 2. Mapear DTO a Entidad
        IssueReport issueReport = IssueReport.builder()
                .equipment(equipment)
                .reportedBy(dto.getReportedBy())
                .priorityIssue(dto.getPriorityIssue())
                .typeIssue(dto.getTypeIssue())
                .descriptionIssue(dto.getDescriptionIssue())
                .createdBy(dto.getReportedBy())
                .build();

        // 3. Guardar (Spring Data Auditing asigna automáticamente created_by, created_at, etc.)
        IssueReport issueSaved = issueReportDao.save(issueReport);

        return entityToDto(issueSaved);
    }

    @Override
    @Transactional
    public void delete(Long id){
        IssueReport issueReport = issueReportDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue not found with id: "+id));

        issueReportDao.delete(issueReport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueReportResponseDto> findByEquipment(Integer equipmentId) {
        List<IssueReport> issueList = issueReportDao.findByEquipment_EquipmentsId(equipmentId);

        return issueList.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IssueReportResponseDto findById(Long id) {
        IssueReport issueReport = issueReportDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Issue report not found with ID: " + id));
        return entityToDto(issueReport);
    }

    private IssueReportResponseDto entityToDto(IssueReport entity){
        return IssueReportResponseDto.builder()
                .id(entity.getId())
                .equipmentId(entity.getEquipment().getEquipmentsId())
                .reportedBy(entity.getReportedBy())
                .priorityIssue(entity.getPriorityIssue())
                .typeIssue(entity.getTypeIssue())
                .descriptionIssue(entity.getDescriptionIssue())
                .createdAt(entity.getCreatedAt())
                .build();
    }

}
