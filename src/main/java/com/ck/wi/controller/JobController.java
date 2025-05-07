package com.ck.wi.controller;

import com.ck.wi.model.entity.Job;
import com.ck.wi.service.IJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
