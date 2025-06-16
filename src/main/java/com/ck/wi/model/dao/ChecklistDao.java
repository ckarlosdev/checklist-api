package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistDao extends CrudRepository<Checklist, Integer> {

    List<Checklist> findByJobAndStatus(Job job, String status);

}
