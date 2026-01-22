package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Checklist;
import com.ck.wi.model.entity.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChecklistDao extends CrudRepository<Checklist, Integer> {

    List<Checklist> findByJobAndStatus(Job job, String status);

    @Query(
            value = " select * from checklists where jobs_id= :jobsId and date= :reportDate ",
            nativeQuery = true
    )
    List<Checklist> findAllByJobIdAndDate(
            @Param("jobsId") Integer jobsId,
            @Param("reportDate") LocalDate reportDate
    );

}
