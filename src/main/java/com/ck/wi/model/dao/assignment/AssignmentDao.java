package com.ck.wi.model.dao.assignment;

import com.ck.wi.model.entity.assignment.Assignment;
import com.ck.wi.model.entity.assignment.AssignmentJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentDao extends CrudRepository<Assignment, Integer> {
    @Query("SELECT DISTINCT a FROM Assignment a " +
            "LEFT JOIN FETCH a.assignmentJobs aj " +
            "LEFT JOIN FETCH aj.assignmentEmployees ae " +
            "WHERE a.assignmentsId = :id AND a.assignmentStatus = '1'")
    Optional<Assignment> findFullAssignmentById(@Param("id") Integer id);

    List<Assignment> findByAssignmentStatus(String status);

}
