package com.ck.wi.controller;

import com.ck.wi.model.dto.EmployeeDto;
import com.ck.wi.model.dto.JobDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Job;
import com.ck.wi.service.IJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "https://oleo-soft.com", methods = {RequestMethod.GET, RequestMethod.POST})
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "https://oleo-soft.com"
})
@RestController
@RequestMapping("/api/v1")
public class JobController {

    @Autowired
    private IJob jobService;

    @PostMapping("job")
    public Job create(@RequestBody Job job){
        return jobService.save(job);
    }

    @PutMapping("job")
    public Job update(@RequestBody Job job){
        return jobService.save(job);
    }

    @DeleteMapping("job/{id}")
    public void delete(@PathVariable Integer id){
        Job job = jobService.findById(id);
        jobService.delete(job);
    }

    @GetMapping("job/{id}")
    public Job showById(@PathVariable Integer id){
        return jobService.findById(id);
    }

    @GetMapping("job/number/{jobNumber}")
    public Job showByNumber(@PathVariable String jobNumber){
        return jobService.findByNumber(jobNumber);
    }

    @GetMapping("job")
    public List<JobDto> showAll(){
        List<Job> jobs = jobService.findAll();
        return jobs.stream()
                .map(job -> JobDto.builder()
                        .jobsId(job.getJobsId())
                        .number(job.getNumber())
                        .type(job.getType())
                        .name(job.getName())
                        .address(job.getAddress())
                        .contractor(job.getContractor())
                        .contact(job.getContact())
                        .status(job.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
