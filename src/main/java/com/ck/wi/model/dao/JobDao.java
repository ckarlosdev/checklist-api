package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Job;
import org.springframework.data.repository.CrudRepository;

public interface JobDao extends CrudRepository<Job, Integer> {
}
