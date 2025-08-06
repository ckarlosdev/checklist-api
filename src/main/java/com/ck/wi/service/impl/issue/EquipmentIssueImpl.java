package com.ck.wi.service.impl.issue;

import com.ck.wi.model.dao.ChecklistDao;
import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.issue.EquipmentIssueDao;
import com.ck.wi.model.dto.issue.EquipmentIssueDto;
import com.ck.wi.model.dto.issue.EquipmentIssueRequestDto;
import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Equipment;
import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.service.issue.IEquipmentIssue;
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

            return equipmentIssueDao.save(equipmentIssue);
        } else {
            throw new IllegalArgumentException("Issue not saved.");
        }
    }

    @Override
    public EquipmentIssue update(EquipmentIssueRequestDto equipmentIssueRequestDto) {

        EquipmentIssue equipmentIssueObj = equipmentIssueDao.findById(equipmentIssueRequestDto.getEquipmentsIssuesId()).orElse(null);

        LocalDateTime today = LocalDateTime.now();

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

            return equipmentIssueDao.save(equipmentIssue);
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
