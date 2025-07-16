package com.ck.wi.controller.issue;

import com.ck.wi.model.dto.issue.EquipmentIssueDto;
import com.ck.wi.model.entity.Issue.EquipmentIssue;
import com.ck.wi.service.issue.IEquipmentIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<EquipmentIssue> showAll(){
        return equipmentIssueService.findAll();
    }

}
