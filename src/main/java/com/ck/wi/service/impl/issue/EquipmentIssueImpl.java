package com.ck.wi.service.impl.issue;

import com.ck.wi.model.dao.ChecklistDao;
import com.ck.wi.model.dao.EquipmentDao;
import com.ck.wi.model.dao.issue.EquipmentIssueDao;
import com.ck.wi.model.dto.issue.EquipmentIssueDto;
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
    public EquipmentIssue update(EquipmentIssueDto equipmentIssueDto) {

        Checklist checklist = checklistDao.findById(equipmentIssueDto.getChecklistsId()).orElse(null);

        Equipment equipment = equipmentDao.findById(equipmentIssueDto.getEquipmentsId()).orElse(null);

        EquipmentIssue equipmentIssueObj = equipmentIssueDao.findById(equipmentIssueDto.getEquipmentsIssuesId()).orElse(null);

        LocalDateTime today = LocalDateTime.now();

        if(checklist != null && equipment != null && equipmentIssueObj != null){
            EquipmentIssue equipmentIssue = EquipmentIssue.builder()
                    .equipmentsIssuesId(equipmentIssueDto.getEquipmentsIssuesId())
                    .checklist(checklist)
                    .equipment(equipment)
                    .flow(equipmentIssueDto.getFlow())
                    .reportedBy(equipmentIssueDto.getReportedBy())
                    .reportedDate(equipmentIssueDto.getReportedDate())
                    .priorityIssue(equipmentIssueDto.getPriorityIssue())
                    .typeIssue(equipmentIssueDto.getTypeIssue())
                    .descriptionIssue(equipmentIssueDto.getDescriptionIssue())
                    .details(equipmentIssueDto.getDetails())
                    .createdBy(equipmentIssueObj.getCreatedBy())
                    .createdDate(equipmentIssueObj.getCreatedDate())
                    .updatedBy(equipmentIssueDto.getUpdatedBy())
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
}
