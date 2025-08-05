package com.ck.wi.controller.issue;

import com.ck.wi.model.dto.issue.EquipmentIssueDto;
import com.ck.wi.model.dto.issue.EquipmentIssueRequestDto;
import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.service.issue.IEquipmentIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173",
        "https://ckarlosdev.github.io"
})
@RestController
@RequestMapping("/api/v1")
public class EquipmentIssueController {

    @Autowired
    private IEquipmentIssue equipmentIssueService;

    @PostMapping("issue")
    public EquipmentIssue createIssue(@RequestBody EquipmentIssueDto equipmentIssueDto){
        return equipmentIssueService.save(equipmentIssueDto);
    }

    @PutMapping("issue")
    public EquipmentIssue updateIssue(@RequestBody EquipmentIssueDto equipmentIssueDto){
        return equipmentIssueService.update(equipmentIssueDto);
    }

    @GetMapping("issue/{id}")
    public EquipmentIssue showById(@PathVariable Integer id){
        return equipmentIssueService.findById(id);
    }

    @GetMapping("issues")
    public List<EquipmentIssueRequestDto> showAll(){
        List<EquipmentIssue> issues = equipmentIssueService.findAll();

        return issues.stream()
                .map(issue ->
                        EquipmentIssueRequestDto.builder()
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
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("issues/{flow}")
    public List<EquipmentIssueRequestDto> showIssuesByFlow(@PathVariable String flow){
        List<EquipmentIssue> issues = equipmentIssueService.findByFlow(flow);

        return issues.stream()
                .map(issue ->
                        EquipmentIssueRequestDto.builder()
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
                                .build())
                .collect(Collectors.toList());
    }

}
