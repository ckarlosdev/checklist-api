package com.ck.wi.service.impl;

import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.dto.JobDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.model.entity.Job;
import com.ck.wi.service.IJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobImpl implements IJob {

    @Autowired
    private JobDao jobDao;

    @Transactional
    @Override
    public Job save(JobDto jobDto) {
        Job job;

        if(jobDto.getJobsId() != null){
            job = jobDao.findById(jobDto.getJobsId())
                    .orElseThrow(() -> new RuntimeException("Job not found"));
        }else{
            job = new Job();
            job.setCreatedBy(jobDto.getUser());
        }

        job.setNumber(jobDto.getNumber());
        job.setType(jobDto.getType());
        job.setName(jobDto.getName());
        job.setAddress(jobDto.getAddress());
        job.setContractor(jobDto.getContractor());
        job.setContact(jobDto.getContact());
        job.setStatus(jobDto.getStatus());
        job.setJobStatus("1");
        job.setUpdatedBy(jobDto.getUser());

        return jobDao.save(job);
    }

    @Transactional(readOnly = true)
    @Override
    public Job findById(Integer id) {
        return jobDao.findById(id)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Job findByNumber(String number) {
        return jobDao.findByNumber(number).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Job> findAll() {
        return (List<Job>) jobDao.findAll();
    }

    @Transactional
    @Override
    public void delete(Job job) {
        jobDao.delete(job);
    }
}
