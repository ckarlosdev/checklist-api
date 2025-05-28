package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.DrEmployeeDto;
import com.ck.wi.model.entity.dailyReport.DrEmployee;
import com.ck.wi.service.dailyReport.IDrEmployee;
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
public class DrEmployeeController {

    @Autowired
    private IDrEmployee drEmployeeService;

    @GetMapping("drEmployee/dailyReport/{dailyReportId}")
    public List<DrEmployeeDto> getDrEmployeesByDailyReportId(@PathVariable Integer dailyReportId){
        List<DrEmployee> drEmployees = drEmployeeService.findByDailyReportId(dailyReportId);

        return drEmployees.stream()
                .map( drEmployee ->
                        DrEmployeeDto.builder()
                                .drEmployeesId(drEmployee.getDrEmployeesId())
                                .dailyReportId(drEmployee.getDailyReportId())
                                .employeesId(drEmployee.getEmployeesId())
                                .name(drEmployee.getName())
                                .title(drEmployee.getTitle())
                                .inHour(drEmployee.getInHour())
                                .outHour(drEmployee.getOutHour())
                                .lunch(drEmployee.getLunch())
                                .ppe(drEmployee.getPpe())
                                .comment(drEmployee.getComment())
                                .status(drEmployee.getStatus())
                                .build())
                .collect(Collectors.toList());

    }
}
