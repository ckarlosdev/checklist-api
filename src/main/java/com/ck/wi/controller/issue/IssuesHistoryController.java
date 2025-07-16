package com.ck.wi.controller.issue;


import com.ck.wi.model.dto.issue.IssuesHistoryDto;
import com.ck.wi.model.entity.Issue.IssuesHistory;
import com.ck.wi.service.issue.IIssuesHistory;
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
public class IssuesHistoryController {

    @Autowired
    private IIssuesHistory issuesHistoryService;

    @PostMapping("issueHistory")
    public IssuesHistory createIssueHistory(@RequestBody IssuesHistoryDto issuesHistoryDto){
        return issuesHistoryService.save(issuesHistoryDto);
    }

    @PutMapping("issueHistory")
    public IssuesHistory updateIssueHistory(@RequestBody IssuesHistoryDto issuesHistoryDto){
        return issuesHistoryService.update(issuesHistoryDto);
    }

    @GetMapping("issueHistory/issue/{issueId}")
    public List<IssuesHistory> getIssueHistory(@PathVariable Integer issueId){
        return issuesHistoryService.findByIssueId(issueId);
    }

}
