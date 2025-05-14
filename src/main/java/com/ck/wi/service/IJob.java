package com.ck.wi.service;

import com.ck.wi.model.entity.Job;

import java.util.List;

public interface IJob {

    Job save(Job job);

    Job findById(Integer id);

    List<Job> findAll();

    void delete(Job job);
}
