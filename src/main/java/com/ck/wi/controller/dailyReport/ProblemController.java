package com.ck.wi.controller.dailyReport;

import com.ck.wi.model.dto.dailyReport.ProblemDto;
import com.ck.wi.model.entity.dailyReport.Problem;
import com.ck.wi.service.dailyReport.IProblem;
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
public class ProblemController {

    @Autowired
    private IProblem problemService;

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

}
