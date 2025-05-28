package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChecklistDao extends CrudRepository<Checklist, Integer> {

    List<Checklist> findByJobAndStatus(Job job, String status);

}
