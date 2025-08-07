package com.ck.wi.service.impl.issue;

import com.ck.wi.model.dao.issue.EquipmentIssueDao;
import com.ck.wi.model.dao.issue.IssuesHistoryDao;
import com.ck.wi.model.dto.issue.IssuesHistoryDto;
import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.model.entity.Issue.IssuesHistory;
import com.ck.wi.service.issue.IIssuesHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IssuesHistoryImpl implements IIssuesHistory {

    @Autowired
    private IssuesHistoryDao issuesHistoryDao;

    @Autowired
    private EquipmentIssueDao equipmentIssueDao;

    @Transactional
    @Override
    public IssuesHistory save(IssuesHistoryDto issuesHistoryDto) {

        EquipmentIssue equipmentIssue = equipmentIssueDao.findById(issuesHistoryDto.getEquipmentsIssuesId())
                .orElseThrow(() -> new IllegalArgumentException("Equipment Issue with ID " + issuesHistoryDto.getEquipmentsIssuesId() + " not found."));

        issuesHistoryDao.updateHistoryStatusByEquipmentIssueId(equipmentIssue.getEquipmentsIssuesId(), "2");

        LocalDateTime today = LocalDateTime.now();

        IssuesHistory issuesHistory = IssuesHistory.builder()
//                .issuesHistoryId(issuesHistoryDto.getIssuesHistoryId())
                .equipmentIssue(equipmentIssue)
                .lastFlow(issuesHistoryDto.getLastFlow())
                .newFlow(issuesHistoryDto.getNewFlow())
                .comments(issuesHistoryDto.getComments())
                .createdBy(issuesHistoryDto.getCreatedBy())
                .createdDate(today)
                .historyStatus("1")
                .build();

        return issuesHistoryDao.save(issuesHistory);
    }

    @Transactional
    @Override
    public IssuesHistory update(IssuesHistoryDto issuesHistoryDto) {

        EquipmentIssue equipmentIssue =  equipmentIssueDao.findById(issuesHistoryDto.getEquipmentsIssuesId()).orElse(null);
        IssuesHistory issuesHistoryObj = issuesHistoryDao.findById(issuesHistoryDto.getIssuesHistoryId()).orElse(null);

        LocalDateTime today = LocalDateTime.now();

        if(equipmentIssue != null && issuesHistoryObj != null){
            IssuesHistory issuesHistory = IssuesHistory.builder()
                    .issuesHistoryId(issuesHistoryDto.getIssuesHistoryId())
                    .equipmentIssue(equipmentIssue)
                    .lastFlow(issuesHistoryDto.getLastFlow())
                    .newFlow(issuesHistoryDto.getNewFlow())
                    .comments(issuesHistoryDto.getComments())
                    .createdBy(issuesHistoryObj.getCreatedBy())
                    .createdDate(issuesHistoryObj.getCreatedDate())
                    .historyStatus(issuesHistoryDto.getStatus())
                    .build();

            return issuesHistoryDao.save(issuesHistory);
        }else {
            throw new IllegalArgumentException("Issue not found.");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<IssuesHistory> findByIssueId(Integer equipmentIssueId) {
        EquipmentIssue equipmentIssue =  equipmentIssueDao.findById(equipmentIssueId).orElse(null);
        return issuesHistoryDao.findByEquipmentIssue(equipmentIssue);
    }

}
