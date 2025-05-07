package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Checklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistDao extends CrudRepository<Checklist, Integer> {
}
