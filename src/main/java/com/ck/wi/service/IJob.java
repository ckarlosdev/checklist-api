package com.ck.wi.service;

import com.ck.wi.model.entity.Job;

public interface IJob {

    Job save(Job job);

    Job findById(Integer id);

    void delete(Job job);
}
