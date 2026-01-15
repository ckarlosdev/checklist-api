package com.ck.wi.model.dao.hazard;

import com.ck.wi.model.entity.hazard.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityDao extends CrudRepository<Activity, Integer> {
    @Query("SELECT a FROM Activity a WHERE a.preTask.preTasksId = :id AND a.status = :status")
    List<Activity> findByPreTaskIdAndStatus(@Param("id") Integer id, @Param("status") String status);
}

