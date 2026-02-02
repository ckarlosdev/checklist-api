package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dao.dailyReport.DailyReportDao;
import com.ck.wi.model.dto.dailyReport.DailyReportDto;
import com.ck.wi.model.dto.dailyReport.DailyReportGralDto;
import com.ck.wi.model.dto.dailyReport.DailyReportSummaryDto;
import com.ck.wi.model.dto.dailyReport.creation.*;
import com.ck.wi.model.dto.request.DailyReportRequest;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import com.ck.wi.service.IEmployee;
import com.ck.wi.service.dailyReport.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
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
public class DailyReportController {

    @Autowired
    private IDailyReport dailyReportService;

    @Autowired
    private IDrEmployee drEmployeeService;

    @Autowired
    private IDrEquipment drEquipmentService;

    @Autowired
    private IDrRental drRentalService;

    @Autowired
    private ITool toolService;

    @PutMapping("/dailyReport")
    public DailyReportCreateDto updateDailyReport(@RequestBody DailyReportCreateDto dailyReportCreateDto){
        return dailyReportService.update(dailyReportCreateDto);
    }

    @PostMapping("/dailyReport/{jobId}")
    public DailyReportCreateDto createDailyReport(
            @RequestBody DailyReportCreateDto dailyReportCreateDto,
            @PathVariable Integer jobId
    ){
        return dailyReportService.save(dailyReportCreateDto, jobId);
    }

    @GetMapping("/dailyReport/dto/{dailyReportId}")
    public DailyReportCreateDto getDailyReportByDailyReportId(@PathVariable Integer dailyReportId){
        return dailyReportService.findByDailyReportID(dailyReportId);
    }

    @GetMapping("/dailyReport")
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

    @GetMapping("/dailyReport/job/{jobNumber}")
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

    @GetMapping("/dailyReport/totalDays/{jobNumber}")
    public Integer getDaysByJobNumber(@PathVariable String jobNumber){
        List<DailyReport> dailyReports = dailyReportService.findByNumber(jobNumber);
        return dailyReports.size();
    }

    @GetMapping("/dailyReport/summary/{jobNumber}")
    public List<DailyReportSummaryDto> getSummaryByJobNumber(@PathVariable String jobNumber){
        return dailyReportService.findSummaryByJobNumber(jobNumber);
    }

    @GetMapping("/dailyReport/jobs")
    public List<DailyReportDto> getDailyReportByNumbers(@RequestBody DailyReportRequest request){
        List<DailyReport> dailyReports = dailyReportService.findByNumbers(request.getJobNumbers());

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

    @GetMapping("/dailyReport/{id}")
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

    @GetMapping("/dailyReport/{number}/by-date")
    public DailyReportDto getDailyReportByIdAndDate(
            @PathVariable String number,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {
        DailyReport dailyReport = dailyReportService.findByNumberAndDate(number, date);

        if (dailyReport == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DailyReport not found for given job id and date");
        }

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

    @GetMapping("/dailyReport/gral/{reportNumber}")
    public ResponseEntity<List<DailyReportGralDto>> getReportSummary(
            @PathVariable String reportNumber)
    {
        return ResponseEntity.ok(dailyReportService.getDrGral(reportNumber));
    }

    @GetMapping("/dailyReport/totals/{jobNumber}/by-date")
    public DrTotalsDto getDrTotals(
            @PathVariable String jobNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        Integer days = dailyReportService.findTotalDaysByJobNumber(jobNumber, date);
        Double hours = drEmployeeService.findTotalHours(jobNumber, date);

        return DrTotalsDto.builder()
                .days(days)
                .hours(hours)
                .build();

    }

    @GetMapping("/dailyReport/resources/{jobNumber}")
    public ResponseEntity<?> getResourcesToCopy(@PathVariable("jobNumber") String jobNumber){

        try {
            // Forzamos un log para ver si llega aquí
            System.out.println("API llamada con jobNumber: " + jobNumber);

            DrResourcesDto dto = DrResourcesDto.builder()
                    .employees(drEmployeeService.getLastReportEmployees(jobNumber))
                    .equipments(drEquipmentService.getLastReportEquipments(jobNumber))
                    .rentals(drRentalService.getLastReportRentals(jobNumber))
                    .tools(toolService.getLastReportTools(jobNumber))
                    .build();
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            // Si hay un error de base de datos o nulo, lo veremos aquí en lugar del 404
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }
}
