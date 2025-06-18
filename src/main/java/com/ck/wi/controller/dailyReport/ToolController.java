package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.ToolDto;
import com.ck.wi.model.dto.request.ToolRequest;
import com.ck.wi.model.entity.dailyReport.Tool;
import com.ck.wi.service.dailyReport.ITool;
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
public class ToolController {

    @Autowired
    private ITool toolService;

    @GetMapping("tool/dailyReport/{dailyReportId}")
    public List<ToolDto> getToolsByDailyReportId(@PathVariable Integer dailyReportId){
        List<Tool> tools = toolService.findByDailyReportId(dailyReportId);

        return tools.stream()
                .map( tool ->
                        ToolDto.builder()
                                .drToolId(tool.getDrToolId())
                                .dailyReportId(tool.getDailyReportId())
                                .qty(tool.getQty())
                                .name(tool.getName())
                                .other(tool.getOther())
                                .type(tool.getType())
                                .comments(tool.getComments())
                                .status(tool.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("tool/dailyReports")
    public List<ToolDto> getToolsByDailyReportId(@RequestBody ToolRequest request){
        List<Tool> tools = toolService.findByDailyReportIds(request.getDailyReportIds());

        return tools.stream()
                .map( tool ->
                        ToolDto.builder()
                                .drToolId(tool.getDrToolId())
                                .dailyReportId(tool.getDailyReportId())
                                .qty(tool.getQty())
                                .name(tool.getName())
                                .other(tool.getOther())
                                .type(tool.getType())
                                .comments(tool.getComments())
                                .status(tool.getStatus())
                                .build())
                .collect(Collectors.toList());
    }
}
