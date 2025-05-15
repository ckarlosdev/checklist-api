package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChecklistDao extends CrudRepository<Checklist, Integer> {

    Optional<Checklist> findByJob(Job job);

}
