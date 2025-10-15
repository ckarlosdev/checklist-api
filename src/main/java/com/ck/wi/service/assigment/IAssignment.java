package com.ck.wi.service.assigment;

import com.ck.wi.model.dto.assignment.AssignmentCreateDto;
import com.ck.wi.model.dto.assignment.AssignmentDto;
import com.ck.wi.model.entity.assignment.Assignment;

import java.util.List;

public interface IAssignment {
    AssignmentDto save(AssignmentCreateDto assignmentCreateDto);

    AssignmentDto getById(Integer assignmentsId);

    List<Assignment> getAssignments();
}
