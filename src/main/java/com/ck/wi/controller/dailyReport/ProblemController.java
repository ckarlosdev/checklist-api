package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.DrEquipmentDto;
import com.ck.wi.model.dto.dailyReport.ProblemDto;
import com.ck.wi.model.dto.request.ProblemRequest;
import com.ck.wi.model.entity.dailyReport.DailyReport;
import com.ck.wi.model.entity.dailyReport.DrEquipment;
import com.ck.wi.model.entity.dailyReport.Problem;
import com.ck.wi.service.dailyReport.IDailyReport;
import com.ck.wi.service.dailyReport.IDrEquipment;
import com.ck.wi.service.dailyReport.IProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/api/v1")
public class ProblemController {

    @Autowired
    private IProblem problemService;

    @Autowired
    private IDrEquipment drEquipmentService;

    @Autowired
    private IDailyReport dailyReportService;

    @GetMapping("problem/drEquipment/{drEquipmentId}")
    public List<ProblemDto>  getProblemByDailyReportId(@PathVariable Integer drEquipmentId){
        List<Problem> problems = problemService.findByDrEquipmentsId(drEquipmentId);

        return problems.stream()
                .map( problem ->
                        ProblemDto.builder()
                                .problemsId(problem.getProblemsId())
                                .drEquipmentsId(problem.getDrEquipmentsId())
                                .type(problem.getType())
                                .priority(problem.getPriority())
                                .description(problem.getDescription())
                                .status(problem.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("problem/drEquipments")
    public List<ProblemDto>  getProblemByDailyReportId(@RequestBody ProblemRequest request){
        List<Problem> problems = problemService.findByDrEquipmentsIds(request.getDrEquipmentIds());

        return problems.stream()
                .map( problem ->
                        ProblemDto.builder()
                                .problemsId(problem.getProblemsId())
                                .drEquipmentsId(problem.getDrEquipmentsId())
                                .type(problem.getType())
                                .priority(problem.getPriority())
                                .description(problem.getDescription())
                                .status(problem.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

    @GetMapping("problem/job/{dailyReportId}")
    public List<ProblemDto> getProblemsByJobNumber(@PathVariable Integer dailyReportId){
        List<Integer> drEquipmentIds = drEquipmentService.findDrEquipmentIdsByDrId(dailyReportId);

        List<Problem> problems = problemService.findByDrEquipmentsIds(drEquipmentIds);

        return problems.stream()
                .map( problem ->
                        ProblemDto.builder()
                                .problemsId(problem.getProblemsId())
                                .drEquipmentsId(problem.getDrEquipmentsId())
                                .type(problem.getType())
                                .priority(problem.getPriority())
                                .description(problem.getDescription())
                                .status(problem.getStatus())
                                .build())
                .collect(Collectors.toList());
    }

}
