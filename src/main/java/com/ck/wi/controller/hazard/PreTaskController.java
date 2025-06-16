package com.ck.wi.controller.hazard;

import com.ck.wi.model.dto.hazard.PreTaskDto;
import com.ck.wi.model.entity.Job;
import com.ck.wi.model.entity.hazard.PreTask;
import com.ck.wi.service.IJob;
import com.ck.wi.service.hazard.IPreTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/api/v1")
public class PreTaskController {

    @Autowired
    private IPreTask preTaskService;

    @Autowired
    private IJob jobService;

    @GetMapping("pretask/{id}")
    public PreTaskDto getPreTaskById(@PathVariable Integer id){
        PreTask preTask = preTaskService.findById(id);

        return PreTaskDto.builder()
                .preTasksId(preTask.getPreTasksId())
                .jobsId(preTask.getJobsId())
                .date(preTask.getDate())
                .supervisor(preTask.getSupervisor())
                .comment(preTask.getComment())
                .build();
    }

    @GetMapping("pretask/{jobNumber}/by-date")
    public PreTaskDto getPretaskByJobNumberAndDate(
            @PathVariable String jobNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date
            ){
        Job job = jobService.findByNumber(jobNumber);

        PreTask preTask = preTaskService.findByJobsIdAndDate(job.getJobsId(), date);

        if(preTask == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pretask not found for given job id and date");
        }

        return PreTaskDto.builder()
                .preTasksId(preTask.getPreTasksId())
                .jobsId(preTask.getJobsId())
                .date(preTask.getDate())
                .supervisor(preTask.getSupervisor())
                .comment(preTask.getComment())
                .build();
    }

    @GetMapping("pt/{id}")
    public ResponseEntity<PreTaskDto> getPretaskWithActivities(@PathVariable Integer id) {
        // Llama al servicio para obtener la Pretask con sus actividades
        Optional<PreTaskDto> pretaskDetailDto = preTaskService.getPretaskWithActivities(id);

        // Devuelve la respuesta HTTP
        return pretaskDetailDto.map(ResponseEntity::ok) // Si se encuentra, devuelve 200 OK con el DTO
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devuelve 404 Not Found
    }


}
