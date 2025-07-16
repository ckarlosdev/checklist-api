package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobDao extends CrudRepository<Job, Integer> {

    Optional<Job> findByNumber(String number);
}
