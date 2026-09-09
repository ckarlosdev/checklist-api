package com.ck.wi.service.impl.issue;

import com.ck.wi.model.dao.ChecklistDao;
import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.issue.EquipmentIssueDao;
import com.ck.wi.model.dto.issue.EquipmentIssueDto;
import com.ck.wi.model.dto.issue.EquipmentIssueRequestDto;
import com.ck.wi.model.dto.issue.IssuesHistoryDto;
import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.model.entity.Issue.IssuesHistory;
import com.ck.wi.service.issue.IEquipmentIssue;
import com.ck.wi.service.issue.IIssueReport;
import com.ck.wi.service.issue.IIssuesHistory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class EquipmentIssueImpl implements IEquipmentIssue {

    @Autowired
    private EquipmentIssueDao equipmentIssueDao;

    @Autowired
    private ChecklistDao checklistDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private IIssuesHistory issuesHistoryService;

    @Autowired
    private IIssueReport issueReportService;

    @Override
    public EquipmentIssue save(EquipmentIssueDto equipmentIssueDto) {

        Checklist checklist = checklistDao.findById(equipmentIssueDto.getChecklistsId()).orElse(null);
        Equipment equipment = equipmentDao.findById(equipmentIssueDto.getEquipmentsId()).orElse(null);

        LocalDateTime today = LocalDateTime.now();

        if(checklist != null && equipment != null){
            EquipmentIssue equipmentIssue = EquipmentIssue.builder()
                    .checklist(checklist)
                    .equipment(equipment)
                    .flow(equipmentIssueDto.getFlow())
                    .reportedBy(equipmentIssueDto.getReportedBy())
                    .reportedDate(equipmentIssueDto.getReportedDate())
                    .priorityIssue(equipmentIssueDto.getPriorityIssue())
                    .typeIssue(equipmentIssueDto.getTypeIssue())
                    .descriptionIssue(equipmentIssueDto.getDescriptionIssue())
                    .details(equipmentIssueDto.getDetails())
                    .createdBy(equipmentIssueDto.getCreatedBy())
                    .createdDate(today)
                    .updatedBy(equipmentIssueDto.getCreatedBy())
                    .updatedDate(today)
                    .issueStatus("1")
                    .build();

            EquipmentIssue updatedIssue = equipmentIssueDao.save(equipmentIssue);


            IssuesHistoryDto historyDto = IssuesHistoryDto.builder()
                    .equipmentsIssuesId(updatedIssue.getEquipmentsIssuesId()) // ID del Issue padre
                    .lastFlow("New insert") // Flujo anterior
                    .newFlow(updatedIssue.getFlow()) // Nuevo flujo
                    .comments("") // Asume que el DTO de Issue tiene un campo para comentarios de actualización
                    .createdBy(equipmentIssueDto.getUpdatedBy()) // Quien actualizó el Issue es quien crea el historial
                    .build();

            issuesHistoryService.save(historyDto);

            if (equipmentIssueDto.getIssueReportId() != null) {
                issueReportService.delete(equipmentIssueDto.getIssueReportId());
            }

            return updatedIssue;
        } else {
            throw new IllegalArgumentException("Issue not saved.");
        }
    }

    @Override
    @Transactional
    public EquipmentIssue update(EquipmentIssueRequestDto dto) {

        EquipmentIssue equipmentIssue = equipmentIssueDao.findById(
                dto.getEquipmentsIssuesId()
        ).orElseThrow(() ->
                new EntityNotFoundException(
                        "Equipment issue not found with id: "
                                + dto.getEquipmentsIssuesId()
                )
        );

        // Guardamos el valor anterior antes de modificar la entidad
        String oldFlow = equipmentIssue.getFlow();

        // Actualización de los campos
        equipmentIssue.setFlow(dto.getFlow());
        equipmentIssue.setReportedBy(dto.getReportedBy());
        equipmentIssue.setReportedDate(dto.getReportedDate());
        equipmentIssue.setPriorityIssue(dto.getPriorityIssue());
        equipmentIssue.setTypeIssue(dto.getTypeIssue());
        equipmentIssue.setDescriptionIssue(dto.getDescriptionIssue());
        equipmentIssue.setDetails(dto.getDetails());

        // Si tu aplicación maneja auditoría manualmente
        equipmentIssue.setUpdatedBy(dto.getUpdatedBy());
        equipmentIssue.setUpdatedDate(LocalDateTime.now());

        equipmentIssue.setIssueStatus("1");

        /*
         * No modificamos:
         * - equipmentsIssuesId
         * - checklist
         * - equipment
         * - createdBy
         * - createdDate
         *
         * porque pertenecen al registro original.
         */

        EquipmentIssue updatedIssue = equipmentIssueDao.save(equipmentIssue);

        // Crear historial únicamente si cambió el flow
        if (!Objects.equals(oldFlow, updatedIssue.getFlow())) {

            IssuesHistoryDto historyDto = IssuesHistoryDto.builder()
                    .equipmentsIssuesId(updatedIssue.getEquipmentsIssuesId())
                    .lastFlow(oldFlow)
                    .newFlow(updatedIssue.getFlow())
                    .comments(dto.getComments())
                    .createdBy(dto.getUpdatedBy())
                    .build();

            issuesHistoryService.save(historyDto);
        }

        return updatedIssue;
    }

    @Override
    public EquipmentIssue findById(Integer equipmentIssueId) {
        return equipmentIssueDao.findById(equipmentIssueId).orElse(null);
    }

    @Override
    public List<EquipmentIssue> findAll() {
        return (List<EquipmentIssue>) equipmentIssueDao.findAll();
    }

    @Override
    public List<EquipmentIssue> findByIssueStatus() {
        return (List<EquipmentIssue>) equipmentIssueDao.findByIssueStatus("1");
    }

    @Override
    public List<EquipmentIssue> findByFlow(String flow) {

        return (List<EquipmentIssue>) equipmentIssueDao.findByFlowAndIssueStatus(flow, "1");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipmentIssueRequestDto> getIssues(
            String flow,
            String search,
            String priority,
            String type,
            Pageable pageable
    ) {
        Page<EquipmentIssue> issuePage = equipmentIssueDao.findIssuesWithFilters(
                flow,
                search,
                priority,
                type,
                pageable
        );

        return issuePage.map(this::entityToDto);
    }

    private EquipmentIssueRequestDto entityToDto(EquipmentIssue issue) {
        return EquipmentIssueRequestDto.builder()
                .equipmentsIssuesId(issue.getEquipmentsIssuesId())
                .equipmentNumber(issue.getEquipment().getNumber())
                .equipmentName(issue.getEquipment().getName())
                .flow(issue.getFlow())
                .reportedBy(issue.getReportedBy())
                .reportedDate(issue.getReportedDate())
                .priorityIssue(issue.getPriorityIssue())
                .typeIssue(issue.getTypeIssue())
                .descriptionIssue(issue.getDescriptionIssue())
                .details(issue.getDetails())
                .createdBy(issue.getCreatedBy())
                .updatedBy(issue.getUpdatedBy())
                .build();
    }
}
