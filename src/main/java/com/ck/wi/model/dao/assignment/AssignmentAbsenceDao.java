package com.ck.wi.model.dao.assignment;

import com.ck.wi.model.entity.assignment.AssignmentAbsence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentAbsenceDao extends CrudRepository<AssignmentAbsence, Integer> {
    List<AssignmentAbsence> findByAssignment_AssignmentsIdAndAbsenceStatus(Integer assignmentId, String status);
}
