package com.ck.wi.model.dao.assignment;

import com.ck.wi.model.entity.assignment.AssignmentEmployee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AssignmentEmployeeDao extends CrudRepository<AssignmentEmployee, Integer> {
    @Query(value = "SELECT * FROM assignments_employees WHERE assignment_job_id = :assignmentJobId", nativeQuery = true)
    List<AssignmentEmployee> findByAssignmentJobId(@Param("assignmentJobId") Integer assignmentJobId);


    @Modifying
    @Transactional
    @Query(
            value = "UPDATE assignments_employees " +
                    "SET ae_status = :status " +
                    "WHERE assignment_job_id = :assignmentJobId",
            nativeQuery = true
    )
    void updateStatusByAssignmentJobId(@Param("assignmentJobId") Integer assignmentJobId,
                                       @Param("status") String status);
}
