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
import com.ck.wi.service.issue.IIssuesHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

//            return equipmentIssueDao.save(equipmentIssue);
            EquipmentIssue updatedIssue = equipmentIssueDao.save(equipmentIssue);


            IssuesHistoryDto historyDto = IssuesHistoryDto.builder()
                    .equipmentsIssuesId(updatedIssue.getEquipmentsIssuesId()) // ID del Issue padre
                    .lastFlow("New insert") // Flujo anterior
                    .newFlow(updatedIssue.getFlow()) // Nuevo flujo
                    .comments("") // Asume que el DTO de Issue tiene un campo para comentarios de actualización
                    .createdBy(equipmentIssueDto.getUpdatedBy()) // Quien actualizó el Issue es quien crea el historial
                    .build();

            issuesHistoryService.save(historyDto);

            return updatedIssue;
        } else {
            throw new IllegalArgumentException("Issue not saved.");
        }
    }

    @Override
    public EquipmentIssue update(EquipmentIssueRequestDto equipmentIssueRequestDto) {

        EquipmentIssue equipmentIssueObj = equipmentIssueDao.findById(equipmentIssueRequestDto.getEquipmentsIssuesId()).orElse(null);

        LocalDateTime today = LocalDateTime.now();
        String oldFlow = equipmentIssueObj.getFlow();

//        if(checklist != null && equipment != null && equipmentIssueObj != null){
        if(equipmentIssueObj != null){
            EquipmentIssue equipmentIssue = EquipmentIssue.builder()
                    .equipmentsIssuesId(equipmentIssueObj.getEquipmentsIssuesId())
                    .checklist(equipmentIssueObj.getChecklist())
                    .equipment(equipmentIssueObj.getEquipment())
                    .flow(equipmentIssueRequestDto.getFlow())
                    .reportedBy(equipmentIssueRequestDto.getReportedBy())
                    .reportedDate(equipmentIssueRequestDto.getReportedDate())
                    .priorityIssue(equipmentIssueRequestDto.getPriorityIssue())
                    .typeIssue(equipmentIssueRequestDto.getTypeIssue())
                    .descriptionIssue(equipmentIssueRequestDto.getDescriptionIssue())
                    .details(equipmentIssueRequestDto.getDetails())
                    .createdBy(equipmentIssueRequestDto.getCreatedBy())
                    .createdDate(equipmentIssueObj.getCreatedDate())
                    .updatedBy(equipmentIssueRequestDto.getUpdatedBy())
                    .updatedDate(today)
                    .issueStatus("1")
                    .build();

//            return equipmentIssueDao.save(equipmentIssue);
            EquipmentIssue updatedIssue = equipmentIssueDao.save(equipmentIssue);


            if (!oldFlow.equals(updatedIssue.getFlow())) {
                IssuesHistoryDto historyDto = IssuesHistoryDto.builder()
                        .equipmentsIssuesId(updatedIssue.getEquipmentsIssuesId()) // ID del Issue padre
                        .lastFlow(oldFlow) // Flujo anterior
                        .newFlow(updatedIssue.getFlow()) // Nuevo flujo
                        .comments(equipmentIssueRequestDto.getComments()) // Asume que el DTO de Issue tiene un campo para comentarios de actualización
                        .createdBy(equipmentIssueRequestDto.getUpdatedBy()) // Quien actualizó el Issue es quien crea el historial
                        .build();

                issuesHistoryService.save(historyDto); // Llama al servicio de historial
            }

            return updatedIssue;
        } else {
            throw new IllegalArgumentException("Issue not saved.");
        }
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
    public List<EquipmentIssue> findByFlow(String flow) {

        return (List<EquipmentIssue>) equipmentIssueDao.findByFlowAndIssueStatus(flow, "1");
    }
}
