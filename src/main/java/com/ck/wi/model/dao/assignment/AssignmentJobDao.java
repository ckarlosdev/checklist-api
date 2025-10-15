package com.ck.wi.model.dao.assignment;

import com.ck.wi.model.entity.assignment.AssignmentJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentJobDao extends CrudRepository<AssignmentJob, Integer> {
//    List<AssignmentJob> findByAssignmentsId(Integer assignmentsId);
//    List<AssignmentJob> findByAssignment_AssignmentsId(Integer id);
    @Query("SELECT aj FROM AssignmentJob aj WHERE aj.assignment.assignmentsId = :id")
    List<AssignmentJob> findByAssignmentId(@Param("id") Integer id);
}
