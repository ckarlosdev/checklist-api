package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Job;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobDao extends CrudRepository<Job, Integer> {

    Optional<Job> findByNumber(String number);

    @Modifying
    @Query("UPDATE Job j SET j.status = :status WHERE j.jobsId IN :ids")
    int updateStatusForJobs(@Param("ids") List<Long> ids, @Param("status") String status);
}
