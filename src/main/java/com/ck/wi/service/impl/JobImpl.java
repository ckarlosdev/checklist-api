package com.ck.wi.service.impl;

import com.ck.wi.model.dao.JobDao;
import com.ck.wi.model.entity.Job;
import com.ck.wi.service.IJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobImpl implements IJob {

    @Autowired
    private JobDao jobDao;

    @Transactional
    @Override
    public Job save(Job job) {
        return jobDao.save(job);
    }

    @Transactional(readOnly = true)
    @Override
    public Job findById(Integer id) {
        return jobDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Job job) {
        jobDao.delete(job);
    }
}
