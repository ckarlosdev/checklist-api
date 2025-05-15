package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JobDao extends CrudRepository<Job, Integer> {

    Optional<Job> findByNumber(String number);
}
