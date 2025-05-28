package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.DailyReportDto;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import com.ck.wi.service.dailyReport.IDailyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com"
})
@RestController
@RequestMapping("/api/v1")
public class DailyReportController {

    @Autowired
    private IDailyReport dailyReportService;

    @GetMapping("dailyReport")
    public List<DailyReportDto> showAll(){
        List<DailyReport> dailyReports = dailyReportService.findAll();

        return dailyReports.stream()
                .map(dailyReport ->
                        DailyReportDto.builder()
                                .dailyReportId(dailyReport.getDailyReportId())
                                .number(dailyReport.getNumber())
                                .address(dailyReport.getAddress())
                                .name(dailyReport.getName())
                                .workingFor(dailyReport.getWorkingFor())
                                .date(dailyReport.getDate())
                                .foreman(dailyReport.getForeman())
                                .crew(dailyReport.getCrew())
                                .description(dailyReport.getDescription())
                                .manTotal(dailyReport.getManTotal())
                                .manHoursTotal(dailyReport.getManHoursTotal())
                                .manOther(dailyReport.getManOther())
                                .equipmentTotal(dailyReport.getEquipmentTotal())
                                .equipHoursTotal(dailyReport.getEquipHoursTotal())
                                .equipmentOther(dailyReport.getEquipmentOther())
                                .issues(dailyReport.getIssues())
                                .createdBy(dailyReport.getCreatedBy())
                                .createdDate(dailyReport.getCreatedDate())
                                .updatedBy(dailyReport.getUpdatedBy())
                                .updatedDate(dailyReport.getUpdatedDate())
                                .status(dailyReport.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("dailyReport/job/{jobNumber}")
    public List<DailyReportDto> getDailyReportsByJobNumber(@PathVariable String jobNumber){
        List<DailyReport> dailyReports = dailyReportService.findByNumber(jobNumber);

        return dailyReports.stream()
                .map(dailyReport ->
                        DailyReportDto.builder()
                                .dailyReportId(dailyReport.getDailyReportId())
                                .number(dailyReport.getNumber())
                                .address(dailyReport.getAddress())
                                .name(dailyReport.getName())
                                .workingFor(dailyReport.getWorkingFor())
                                .date(dailyReport.getDate())
                                .foreman(dailyReport.getForeman())
                                .crew(dailyReport.getCrew())
                                .description(dailyReport.getDescription())
                                .manTotal(dailyReport.getManTotal())
                                .manHoursTotal(dailyReport.getManHoursTotal())
                                .manOther(dailyReport.getManOther())
                                .equipmentTotal(dailyReport.getEquipmentTotal())
                                .equipHoursTotal(dailyReport.getEquipHoursTotal())
                                .equipmentOther(dailyReport.getEquipmentOther())
                                .issues(dailyReport.getIssues())
                                .createdBy(dailyReport.getCreatedBy())
                                .createdDate(dailyReport.getCreatedDate())
                                .updatedBy(dailyReport.getUpdatedBy())
                                .updatedDate(dailyReport.getUpdatedDate())
                                .status(dailyReport.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("dailyReport/{id}")
    public DailyReportDto getDailyReportByID(@PathVariable Integer id){
        DailyReport dailyReport = dailyReportService.findById(id);

        return DailyReportDto.builder()
                .dailyReportId(dailyReport.getDailyReportId())
                .number(dailyReport.getNumber())
                .address(dailyReport.getAddress())
                .name(dailyReport.getName())
                .workingFor(dailyReport.getWorkingFor())
                .date(dailyReport.getDate())
                .foreman(dailyReport.getForeman())
                .crew(dailyReport.getCrew())
                .description(dailyReport.getDescription())
                .manTotal(dailyReport.getManTotal())
                .manHoursTotal(dailyReport.getManHoursTotal())
                .manOther(dailyReport.getManOther())
                .equipmentTotal(dailyReport.getEquipmentTotal())
                .equipHoursTotal(dailyReport.getEquipHoursTotal())
                .equipmentOther(dailyReport.getEquipmentOther())
                .issues(dailyReport.getIssues())
                .createdBy(dailyReport.getCreatedBy())
                .createdDate(dailyReport.getCreatedDate())
                .updatedBy(dailyReport.getUpdatedBy())
                .updatedDate(dailyReport.getUpdatedDate())
                .status(dailyReport.getStatus())
                .build();
    }
}
